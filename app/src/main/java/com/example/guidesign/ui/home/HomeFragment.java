package com.example.guidesign.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guidesign.Handler;
import com.example.guidesign.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    String username,act1,act2,act3,act4,act5,act6,act7,act8;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        username = this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("username1", "");
        //textView.setText("請選擇您的寵物"+username+"\n動作評量AI小幫手");
        initView(root);





        return root;
    }

    private void initView(View root) {
        showList(root);
    }

    private void showList(final View root){
        //取得錄製紀錄

        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://140.116.70.157/AndroidFileUpload/task.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        act1 ="ss";act2 ="ss";act3 ="ss";act4 ="ss";act5 ="ss";act6 ="ss";act7 ="ss";act8 ="ss";
                        ArrayList a = new ArrayList();

                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("patient");
                            Log.d("SQL server",String.valueOf(array.length()));
                            for ( int i = 0; i<array.length();i++){
                                JSONObject patientObj = array.getJSONObject(i);
                                if(patientObj.getString("Patient").equals(username) &&  patientObj.getInt("Action")==1)
                                {
                                    act1 = "摸同側耳朵";
                                    a.add(act1);
                                }

                                if(patientObj.getString("Patient").equals(username) &&  patientObj.getInt("Action")==2)
                                {
                                    act2 = "摸對側膝蓋";
                                    a.add(act2);

                                }
                                if(patientObj.getString("Patient").equals(username) &&  patientObj.getInt("Action")==3)
                                {
                                    act3 = "摸屁股";
                                    a.add(act3);

                                }
                                if(patientObj.getString("Patient").equals(username) &&  patientObj.getInt("Action")==4)
                                {
                                    act4 = "手向前抬-手肘伸直";
                                    a.add(act4);
                                }
                                if(patientObj.getString("Patient").equals(username) &&  patientObj.getInt("Action")==5)
                                {
                                    act5 = "翻掌-手肘彎曲";
                                    a.add(act5);
                                }
                                if(patientObj.getString("Patient").equals(username) &&  patientObj.getInt("Action")==6)
                                {
                                    act6 = "手向側邊平舉-手肘伸直";
                                    a.add(act6);
                                }
                                if(patientObj.getString("Patient").equals(username) &&  patientObj.getInt("Action")==7)
                                {
                                    act7 = "手向上抬-手肘伸直";
                                    a.add(act7);
                                }
                                if(patientObj.getString("Patient").equals(username) &&  patientObj.getInt("Action")==8)
                                {
                                    act8 = "翻掌-手肘伸直";
                                    a.add(act8);
                                }



                            }
                            final TextView text_acc = root.findViewById(R.id.task);
                            for ( int i = 0; i<array.length();i++){
                                int s=i+1;
                                text_acc.append("\n"+s+"."+a.get(i));
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
                return params;
            }

        };

        Handler.getInstance(getContext()).addToRequestQue(stringRequest);
    }






}