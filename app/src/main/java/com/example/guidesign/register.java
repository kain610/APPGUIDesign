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
    private String status;
    private String result;
    private EditText eduser,edpwd,edcheckpwd,edphone;
    public   int local;
    private Button sumbit;
    public static final String register_URL = "http://140.116.70.173/AndroidFileUpload/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sumbit = findViewById(R.id.regist);
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

        init();
        initListener();
    }



    private void init() {
        eduser = (EditText)findViewById(R.id.user);
        edpwd = (EditText)findViewById(R.id.pwd);
        edcheckpwd = (EditText)findViewById(R.id.checkpwd);
        edphone = (EditText)findViewById(R.id.phone);

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


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(register_URL);
            userName =eduser.getText().toString();
            pwd = edpwd.getText().toString();
            checkpwd = edcheckpwd.getText().toString();
            phone = edphone.getText().toString();
            status = String.valueOf(local);




            try {
                List<NameValuePair> vars = new ArrayList<NameValuePair>();
                vars.add(new BasicNameValuePair("username", userName));
                vars.add(new BasicNameValuePair("pwd", pwd));
                vars.add(new BasicNameValuePair("checkpwd", checkpwd));
                vars.add(new BasicNameValuePair("phone", phone));
                vars.add(new BasicNameValuePair("status", status));


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

  /*  private Runnable mutiThread = new Runnable(){
        public void run()
        {
            // 當這個執行緒完全跑完後執行
            userName = String.valueOf(eduser);
            pwd = String.valueOf(edpwd);
            checkpwd = String.valueOf(edcheckpwd);
            phone = String.valueOf(edphone);
            status = String.valueOf(local);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost method = new HttpPost(register_URL);

            try {


                List<NameValuePair> vars = new ArrayList<NameValuePair>();
                vars.add(new BasicNameValuePair("username", userName));
                vars.add(new BasicNameValuePair("pwd", pwd));
                vars.add(new BasicNameValuePair("checkpwd", checkpwd));
                vars.add(new BasicNameValuePair("phone", phone));
                vars.add(new BasicNameValuePair("status", status));
                method.setEntity(new UrlEncodedFormEntity(vars,HTTP.UTF_8));
                HttpResponse httpResponse = httpclient.execute(method);
                HttpEntity resEntity = httpResponse.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity);
                }




            }  catch (Exception e) {
                e.printStackTrace();
                    } finally {
                httpclient.getConnectionManager().shutdown();
            }



        }
    };*/







}


