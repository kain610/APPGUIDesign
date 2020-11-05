package com.example.guidesign;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class fluency extends Fragment {


    private static int act;
    private static String username, password, direction,act1;
    private int DATA_COUNT = 0;
    private TextView title;
    private LineChart fluency_chart;
    List<Entry> fluency_chartData = new ArrayList<>();
    List<String> fluency_chartLabels = new ArrayList<>();
    List<LineDataSet> fluency_dataSets = new ArrayList<>();

    public fluency() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fluency, container, false);
        int act2 = (int) getArguments().get("act");
        String act3 = (String) getArguments().get("act1");
        String str = (String)getArguments().get("username");
        String str1 = (String)getArguments().get("password");
        String str2 = (String)getArguments().get("direction");
        direction = str2;
        act = act2;
        act1 = act3;
        username = str;
        password = str1;
        initView(root);
        initListener(root);
        initchart(root);


        return root;
    }
    private void initView(View root){

        title = (TextView)root.findViewById(R.id.resultTitle);


        Log.d("SQL server","Before showlist");
        showList();

    }
    private void initListener(View root){

        if (direction.equals("Left"))
            title.setText(act1+"  左手_流暢度圖表");
        else
            title.setText(act1+"  右手_流暢度圖表");
    }
    private void initchart(View root)
    {
        fluency_chart=(LineChart)root.findViewById(R.id.fluency_chart);

        // enable touch gestures

    }
    private void showList(){
        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://140.116.70.173/AndroidFileUpload/sql.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("patient");
                            Log.d("SQL server",String.valueOf(array.length()));



                            for ( int i = 0; i<array.length();i++){
                                JSONObject patientObj = array.getJSONObject(i);


                                if(patientObj.getString("Name").equals(username) && patientObj.getString("Direction").equals(direction) && patientObj.getInt("Action")==act)
                                {
                                    Log.d("SQL server",patientObj.getString("Name")+" "+patientObj.getString("Complete")+" "+patientObj.getString("Time"));
                                    Log.d("SQL server",patientObj.getString("Direction")+", "+patientObj.getString("Action"));
                                    Patient p = new Patient(patientObj.getString("Name"),patientObj.getString("Complete"),patientObj.getString("Fluency"),patientObj.getString("Time"));

                                    //draw

                                    fluency_chartData.add(new Entry(Float.valueOf(patientObj.getString("Fluency")), DATA_COUNT));
                                    fluency_chartLabels.add(patientObj.getString("Time"));

                                    DATA_COUNT++;


                                    //draw
                                }

                            }
                            LineDataSet dataSetB = new LineDataSet(fluency_chartData, "流暢度折現圖");
                            fluency_dataSets.add(dataSetB); // add the datasets
                            fluency_chart.setDescription(null);
                            fluency_chart.setData(new LineData(fluency_chartLabels, fluency_dataSets));
                            fluency_chart.invalidate();




                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("SQL server", "Failed with error msg:\t" + error.getMessage());
                Log.d("SQL server", "Error StackTrace: \t" + error.getStackTrace());
            }
        }){

        };
        Handler.getInstance(getContext()).addToRequestQue(stringRequest);
    }

}
