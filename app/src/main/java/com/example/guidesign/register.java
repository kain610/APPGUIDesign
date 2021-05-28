package com.example.guidesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guidesign.ui.home.HomeFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class register extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String userName;
    private String pwd;
    private String checkpwd;
    private String phone;
    private String age;
    private String sex;
    private String status;
    private String checkpet;
    private String result;
    private EditText eduser,edpwd,edcheckpwd,edphone,edage;
    private RadioButton boy;
    private RadioButton gril;
    private RadioGroup radioGroup;
    public   int local;
    private Button sumbit;
    public static final String register_URL = "http://140.116.70.157/AndroidFileUpload/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sumbit = findViewById(R.id.regist);
        ImageView cpet = (ImageView) findViewById(R.id.pet);
        Spinner spinner = findViewById(R.id.spinner);
        final String[] place = {"成醫復健部","成醫老年科"};
        ArrayAdapter<String> placeList = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                place);spinner.setAdapter(placeList);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               local = position+1;
            }

                       @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        Bundle bundle = getIntent().getExtras();
        String pet = bundle.getString("pet");
        if(pet.equals("1")){
            cpet.setImageResource(R.drawable.design_31);
            checkpet= String.valueOf('1');

        }else if(pet.equals("2")){
            cpet.setImageResource(R.drawable.design_32);
            checkpet=String.valueOf('2');
        }


        init();
        initListener();
    }




    private void init() {
        //取得輸入框內容
        eduser = (EditText)findViewById(R.id.user);
        edpwd = (EditText)findViewById(R.id.pwd);
        edcheckpwd = (EditText)findViewById(R.id.checkpwd);
        edphone = (EditText)findViewById(R.id.phone);
        edage = (EditText)findViewById(R.id.age);
        radioGroup = (RadioGroup)findViewById(R.id.sex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int arg1) {
                boy = group.findViewById(R.id.boy);
                gril = group.findViewById(R.id.gril);
                if (boy.isChecked()){

                    sex="Male";
                }
                if(gril.isChecked()){

                    sex="Female";
                }
            }
        });


    }
    private void initListener() {

        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AccountToServer().execute();
            }

        });
    }

    public class AccountToServer extends AsyncTask<Void, Integer, String> {



        protected void onPreExecute() {


            super.onPreExecute();
        }



        @Override
        protected String doInBackground(Void... params) {

            return accountFile();

        }


        private String accountFile() {

            String responseString = null;
            //傳資料進資料庫
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(register_URL);
            userName =eduser.getText().toString();
            pwd = edpwd.getText().toString();
            checkpwd = edcheckpwd.getText().toString();
            phone = edphone.getText().toString();
            age =edage.getText().toString();
            status = String.valueOf(local);




            try {
                List<NameValuePair> vars = new ArrayList<NameValuePair>();
                vars.add(new BasicNameValuePair("username", userName));
                vars.add(new BasicNameValuePair("pwd", pwd));
                vars.add(new BasicNameValuePair("checkpwd", checkpwd));
                vars.add(new BasicNameValuePair("phone", phone));
                vars.add(new BasicNameValuePair("status", status));
                vars.add(new BasicNameValuePair("age", age));
                vars.add(new BasicNameValuePair("sex", sex));
                vars.add(new BasicNameValuePair("pet", checkpet));


                httppost.setEntity(new UrlEncodedFormEntity(vars,HTTP.UTF_8));

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            httpclient.getConnectionManager().shutdown();

            return responseString;


        }


        @Override
        protected void onPostExecute(String result) {

            Log.e(TAG, "Response from server: " + result);



            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }
    private void showAlert(String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
        builder.setMessage(message).setTitle("提醒")
                .setCancelable(false)
                .setPositiveButton("登入", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(register.this, LoginActivity.class);

                        startActivity(intent);

                    }
                }).setNegativeButton("重新註冊",null)


        ;
       /* builder.setNegativeButton("重新錄製", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent is = new Intent(UploadActivity.this, ActionActivity.class);
                startActivity(is);
            }
        });*/

        AlertDialog alert = builder.create();
        alert.show();
    }









}


