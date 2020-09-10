package com.example.guidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class froget extends AppCompatActivity {

    private EditText et_phone , et_user;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String f_user , f_phone;
    private Button sumbit;
    public static final String found_URL = "http://140.116.70.173/AndroidFileUpload/register.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_froget);
        sumbit = findViewById(R.id.found);
        init();
        initListener();
    }



    private void init() {
        et_phone = findViewById(R.id.f_phone);
        et_user = findViewById(R.id.f_user);
    }
    private void initListener() {
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FoundToServer();
            }

        });
    }


    public class FoundToServer extends AsyncTask<Void, Integer, String> {



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
            HttpPost httppost = new HttpPost(found_URL);
            f_user = et_user.getText().toString();
            f_phone = et_phone.getText().toString();





            try {
                List<NameValuePair> vars = new ArrayList<NameValuePair>();
                vars.add(new BasicNameValuePair("username", f_user));
                vars.add(new BasicNameValuePair("phone", f_phone));



                httppost.setEntity(new UrlEncodedFormEntity(vars, HTTP.UTF_8));

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


            super.onPostExecute(result);
        }

    }
}