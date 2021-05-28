package com.example.guidesign;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.guidesign.PatientAdapter;
import com.example.guidesign.Patient;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private Button btnCertify;
    private Button btnReg;
    private Button btnFor;
    private EditText username;
    String name ,coin,day;
    private EditText password;
    private CheckBox remember;
    private String getuser,getpass;

    private int haveName = 0;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);



        initView();
        initListener();
        restoreInfo();

        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
        if(EasyPermissions.hasPermissions(this,perms)){

            // Create an instance of Camera


        }else{
            EasyPermissions.requestPermissions(this, "我們需要這些權限來啟動錄影功能!",123,perms);
        }
        // listView = (ListView) findViewById(R.id.listView);


    }

    private void initView(){
        btnCertify = (Button) findViewById(R.id.btn_certify);
        btnReg = (Button) findViewById(R.id.btn_reg);
        btnFor = (Button) findViewById(R.id.btn_forget);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        remember = (CheckBox)findViewById(R.id.remember);
        remember.setChecked(true);

    }

    private void initListener(){

        btnCertify.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View arg0, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    btnCertify.setBackgroundResource(R.color.colorPrimaryDark);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色
                    getuser=username.getText().toString();
                    getpass=password.getText().toString();
                    btnCertify.setBackgroundResource(R.color.colorPrimary);
                    checkName();
                }
                return false;
            }
        });

        btnReg.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View arg0, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    btnReg.setBackgroundResource(R.color.colorPrimaryDark);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色

                    btnReg.setBackgroundResource(R.color.colorPrimary);
                    Intent intent = new Intent(LoginActivity.this, PetActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        btnFor.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View arg0, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //按下的時候改變背景及顏色
                    btnFor.setBackgroundResource(R.color.colorPrimaryDark);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {  //起來的時候恢復背景與顏色

                    btnFor.setBackgroundResource(R.color.colorPrimary);
                    Intent intent = new Intent(LoginActivity.this, froget.class);
                    startActivity(intent);
                }
                return false;
            }
        });

/*
        btnCertify.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                // prepare record
                getuser=username.getText().toString();
                getpass=password.getText().toString();
                checkName();


            }
        });

*/


    }

    private boolean checkName(){
        Log.d("SQL server","start checking name");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://140.116.70.157/AndroidFileUpload/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("users");

                            for ( int i = 0; i<array.length();i++){
                                JSONObject patientObj = array.getJSONObject(i);
                                Log.d("SQL server",patientObj.getString("Account"));
                                if(patientObj.getString("Account").equals(getuser) && patientObj.getString("Password").equals(getpass))
                                {
                                    Log.d("SQL server","Do have "+getuser);
                                    name = patientObj.getString("Name");
                                    coin = patientObj.getString("Coins");
                                    day = patientObj.getString("Days");

                                    haveName=1;
                                }


                            }
                            if (haveName==0)
                                Toast.makeText(getApplicationContext(),
                                        "無此使用者或密碼錯誤", Toast.LENGTH_LONG)
                                        .show();
                            else
                            {
                                Intent a = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                a.putExtra("username", getuser);
                                a.putExtra("password", getpass);
                                a.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                if (remember.isChecked()){
                                    String usr=username.getText().toString();
                                    String pwd=password.getText().toString();
                                    memInfo(usr,pwd);
                                }else{
                                    SharedPreferences.Editor et=getSharedPreferences("data",0).edit();
                                    String usr=username.getText().toString();
                                    String pwd=password.getText().toString();
                                    memInfo(usr,pwd);
                                    et.remove("username");
                                    et.remove("password");
                                    // et.clear();
                                    et.commit();
                                }


                                startActivity(a);
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            haveName=0;
                            Toast.makeText(getApplicationContext(),
                                    "無此使用者或密碼錯誤", Toast.LENGTH_LONG)
                                    .show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("SQL server", "Failed with error msg:\t" + error.getMessage());
                Log.d("SQL server", "Error StackTrace: \t" + error.getStackTrace());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getuser=username.getText().toString();
                getpass=password.getText().toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", getuser);
                params.put("password", getpass);
                return params;
            }

        };
        Handler.getInstance(getApplicationContext()).addToRequestQue(stringRequest);
        if (haveName==1)
            return true;
        else
            return false;


    }
    //紀錄帳號密碼
    private void memInfo(String usr,String pwd){
        SharedPreferences.Editor editor=getSharedPreferences("data",0).edit();
        editor.putString("username",usr);
        editor.putString("password",pwd);
        editor.putString("username1",usr);
        editor.putString("password1",pwd);
        editor.putString("Name",name);
        editor.putString("Coins",coin);
        editor.putString("Days",day);
        editor.commit();
    }

    private void restoreInfo(){
        SharedPreferences sp=getSharedPreferences("data",0);
        username.setText(sp.getString("username",""));
        password.setText(sp.getString("password",""));

    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
