package com.example.guidesign.ui.gallery;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guidesign.Handler;
import com.example.guidesign.Patient;
import com.example.guidesign.PatientAdapter;
import com.example.guidesign.R;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;



public class GalleryFragment extends Fragment {
    View v;
    ArrayList<DataModel> dataModels;
    String act1,act2,act3,act4,act5,act6,act7,act8;
    String username;

    ListView lv;
    private static CustomAdapter adapter;


    private GalleryViewModel galleryViewModel;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);

        v = inflater.inflate(R.layout.fragment_gallery, container,false);

        lv = (ListView)v.findViewById(R.id.listView1);
        username = this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("username1", "");


        initView(v);




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("position", String.valueOf(position));
                showaction fragment = new showaction();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();



            }
        });





        return v;
    }
    private void initView(View v){

                showList();

    }
    private void showList(){
        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://140.116.70.173/AndroidFileUpload/act.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("patient");
                            Log.d("SQL server",String.valueOf(array.length()));
                            for ( int i = 0; i<array.length();i++){
                                JSONObject patientObj = array.getJSONObject(i);
                                if(patientObj.getString("Name").equals(username) &&  patientObj.getInt("Action")==1)
                                {
                                    act1 = patientObj.getString("Time");
                                }
                                if(patientObj.getString("Name").equals(username) &&  patientObj.getInt("Action")==2)
                                {
                                    act2 = patientObj.getString("Time");
                                }
                                if(patientObj.getString("Name").equals(username) &&  patientObj.getInt("Action")==3)
                                {
                                    act3 = patientObj.getString("Time");
                                }
                                if(patientObj.getString("Name").equals(username) &&  patientObj.getInt("Action")==4)
                                {
                                    act4 = patientObj.getString("Time");
                                }
                                if(patientObj.getString("Name").equals(username) &&  patientObj.getInt("Action")==5)
                                {
                                    act5 = patientObj.getString("Time");
                                }
                                if(patientObj.getString("Name").equals(username) &&  patientObj.getInt("Action")==6)
                                {
                                    act6 = patientObj.getString("Time");
                                }
                                if(patientObj.getString("Name").equals(username) &&  patientObj.getInt("Action")==7)
                                {
                                    act7 = patientObj.getString("Time");
                                }
                                if(patientObj.getString("Name").equals(username) &&  patientObj.getInt("Action")==8)
                                {
                                    act8 = patientObj.getString("Time");
                                }
                                dataModels= new ArrayList<>();

                                dataModels.add(new DataModel("動作一","1",act1));
                                dataModels.add(new DataModel("動作二","2", act2));
                                dataModels.add(new DataModel("動作三","3", act3));
                                dataModels.add(new DataModel("動作四","4",act4));
                                dataModels.add(new DataModel("動作五","5",act5));
                                dataModels.add(new DataModel("動作六","6", act6));
                                dataModels.add(new DataModel("動作七","7", act7));
                                dataModels.add(new DataModel("動作八","8", act8));
                                adapter= new CustomAdapter(dataModels,getContext());

                                lv.setAdapter(adapter);


                            }



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