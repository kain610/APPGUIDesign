package com.example.guidesign;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class history extends Fragment {

    private Button btnRecord;
    private Button btnRecord1;
    private ListView listView;
    List<Patient> patientList;

    private static int act;
    private static String username, password, direction,act1;
    private int DATA_COUNT = 0;
    private TextView title;

    public history() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_history, container, false);
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
        configureButton(root);





        return  root;
    }
    private void initView(View root){

        listView = (ListView)root.findViewById(R.id.patient_data);
        title = (TextView)root.findViewById(R.id.resultTitle);
        patientList = new ArrayList<>();

        Log.d("SQL server","Before showlist");
        showList();

    }
    private void initListener(View root){

        if (direction.equals("Left"))
            title.setText(act1+"  左手");
        else
            title.setText(act1+"  右手");
    }
    private void showList(){
        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://140.116.70.157/AndroidFileUpload/sql.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("patient");
                            Log.d("SQL server",String.valueOf(array.length()));

                            Patient title = new Patient("名字","完成度","順暢度","時間");
                            patientList.add(title);

                            for ( int i = 0; i<array.length();i++){
                                JSONObject patientObj = array.getJSONObject(i);


                                if(patientObj.getString("Name").equals(username) && patientObj.getString("Direction").equals(direction) && patientObj.getInt("Action")==act)
                                {
                                    Log.d("SQL server",patientObj.getString("Name")+" "+patientObj.getString("Complete")+" "+patientObj.getString("Time"));
                                    Log.d("SQL server",patientObj.getString("Direction")+", "+patientObj.getString("Action"));
                                    Patient p = new Patient(patientObj.getString("Name"),patientObj.getString("Complete"),patientObj.getString("Fluency"),patientObj.getString("Time"));
                                    patientList.add(p);
                                    //draw


                                    DATA_COUNT++;


                                    //draw
                                }

                            }


                            PatientAdapter adapter = new PatientAdapter(patientList,getContext());
                            listView.setAdapter(adapter);
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

    private void configureButton(View root) {
        btnRecord = (Button) root.findViewById(R.id.chart);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("act", act);
                bundle.putString("act1", act1);
                bundle.putString("username",username);
                bundle.putString("password",password);
                bundle.putString("direction",direction);
                ChartFragment fragment =  new ChartFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



    }

}
