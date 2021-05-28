package com.example.guidesign;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ShopFragment extends Fragment {


    private ImageButton btnRecord,btnRecord1;
    private ImageView pet;
    private static String username, password,coin;
    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        String str = (String)getArguments().get("username");
        String str1 = (String)getArguments().get("password");
        username = str;
        password = str1;

        initView(root);

        return root;

    }

    private void initView(View root) {
        btnRecord =  root.findViewById(R.id.btn_shop);
        btnRecord1 =  root.findViewById(R.id.btn_candy);
        pet = root.findViewById(R.id.peteat);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pet.setImageResource(R.drawable.design_19);
                showList();
            }

        });
        btnRecord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pet.setImageResource(R.drawable.design_19);
                showList1();
            }

        });
    }
    private void showList(){
        //取得錄製紀錄

        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://140.116.70.157/AndroidFileUpload/shop.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("users");
                            Log.d("SQL server",String.valueOf(array.length()));
                            for ( int i = 0; i<array.length();i++){
                                JSONObject patientObj = array.getJSONObject(i);
                                if(patientObj.getString("Account").equals(username) &&  patientObj.getString("Password").equals(password))
                                {

                                }





                            }


                        } catch (JSONException e){


                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("SQL server", "Failed with error msg:\t" + error.getMessage());
                Log.d("SQL server", "Error StackTrace: \t" + error.getStackTrace());
            }
        }){
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };

        Handler.getInstance(getContext()).addToRequestQue(stringRequest);
    }
    private void showList1(){
        //取得錄製紀錄

        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://140.116.70.157/AndroidFileUpload/shop1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("users");
                            Log.d("SQL server",String.valueOf(array.length()));
                            for ( int i = 0; i<array.length();i++){
                                JSONObject patientObj = array.getJSONObject(i);
                                if(patientObj.getString("Account").equals(username) &&  patientObj.getString("Password").equals(password))
                                {

                                }





                            }


                        } catch (JSONException e){


                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("SQL server", "Failed with error msg:\t" + error.getMessage());
                Log.d("SQL server", "Error StackTrace: \t" + error.getStackTrace());
            }
        }){
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };

        Handler.getInstance(getContext()).addToRequestQue(stringRequest);
    }
}