package com.example.guidesign;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private static int act;
    private static String username, password, direction,act1;
    private int DATA_COUNT = 0;
    private TextView title;
    private ListView listView;
    private ImageButton btnRecord,btnRecord1;
    List<Patient> patientList;

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chart, container, false);
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

        configureButton(root);





        return  root;
    }

    private void initView(View root) {
        listView = (ListView)root.findViewById(R.id.patient_data);
        title = (TextView)root.findViewById(R.id.resultTitle);
        patientList = new ArrayList<>();

        Log.d("SQL server","Before showlist");

    }



    private void configureButton(View root) {
        btnRecord =  root.findViewById(R.id.btn_com);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("act", act);
                bundle.putString("act1", act1);
                bundle.putString("username",username);
                bundle.putString("password",password);
                bundle.putString("direction",direction);
                complete fragment =  new complete();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnRecord1 = root.findViewById(R.id.btn_flu);
        btnRecord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("act", act);
                bundle.putString("act1", act1);
                bundle.putString("username",username);
                bundle.putString("password",password);
                bundle.putString("direction",direction);
                fluency fragment =  new fluency();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}