package com.example.guidesign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guidesign.ui.home.HomeFragment;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.android.volley.VolleyLog.TAG;


public class prUpdate extends Fragment {

    Button btnUpadte;
    String acc1,pwd1,na1,ph1,check1,status;
    private EditText n,p,pw,ch;
    public   int local;
    public static final String Update_URL = "http://140.116.70.157/AndroidFileUpload/update.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pr_update, container,false);
        btnUpadte = root.findViewById(R.id.btn_update);
        TextView acc = root.findViewById(R.id.ed_acc);
        EditText na = root.findViewById(R.id.ed_na);
        EditText ph = root.findViewById(R.id.ed_ph);
        EditText pwd = root.findViewById(R.id.ed_pwd);
        EditText  check = root.findViewById(R.id.ed_cp);
        String str = (String)getArguments().get("account");
        String str1 = (String)getArguments().get("name");
        String str2 = (String)getArguments().get("phone");
        String str3  = this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("password1", "");
        acc.setText(str);
        na.setText(str1);
        ph.setText(str2);
        pwd.setText(str3);
        check.setText(str3);
        Spinner spinner = root.findViewById(R.id.spinner1);
        final String[] place = {"成醫復健部","成醫老年科"};
        ArrayAdapter<String> placeList = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                place);
        spinner.setAdapter(placeList);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                local = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        acc1 =acc.getText().toString();
        init(root);
        initListener();



        return root;
    }

    private void init(View v) {
         n = v.findViewById(R.id.ed_na);
         p = v.findViewById(R.id.ed_ph);
         pw = v.findViewById(R.id.ed_pwd);
          ch = v.findViewById(R.id.ed_cp);


    }

    private void initListener() {
        btnUpadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new showperson().execute();
            }
        });
    }

    public class showperson extends AsyncTask<Void, Integer, String> {



        protected void onPreExecute() {


            super.onPreExecute();
        }



        @Override
        protected String doInBackground(Void... params) {

            return accountFile();

        }


        private String accountFile() {
            status = String.valueOf(local);
            pwd1 = pw.getText().toString();
            na1 = n.getText().toString();
            ph1 = p.getText().toString();
            check1 = ch.getText().toString();

            String responseString = null;


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Update_URL);






            try {
                List<NameValuePair> vars = new ArrayList<NameValuePair>();

                vars.add(new BasicNameValuePair("account", acc1));
                vars.add(new BasicNameValuePair("name", na1));
                vars.add(new BasicNameValuePair("phone", ph1));
                vars.add(new BasicNameValuePair("password", pwd1));
                vars.add(new BasicNameValuePair("checkpwd", check1));
                vars.add(new BasicNameValuePair("place", status));




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


            showAlert(result);
            // showing the server response in an alert dialog


            super.onPostExecute(result);
        }

    }
    private void showAlert(String message) {
        if (message.equals("更新成功!")==true){
            memInfo();
                    }




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message).setTitle("提醒")
                .setCancelable(false)
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeFragment fragment =  new HomeFragment();

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                }).setNegativeButton("重新更改",null);
       /* builder.setNegativeButton("重新錄製", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent is = new Intent(UploadActivity.this, ActionActivity.class);
                startActivity(is);
            }
        });*/

        AlertDialog alert = builder.create();
        alert.show();
    }
    private void memInfo(){
        SharedPreferences.Editor editor=this.getActivity().getSharedPreferences("data",0).edit();
        editor.putString("Name",na1);
        editor.commit();
    }

}