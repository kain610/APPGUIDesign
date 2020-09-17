package com.example.guidesign.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guidesign.Handler;
import com.example.guidesign.R;
import com.example.guidesign.hidire;
import com.example.guidesign.history;
import com.example.guidesign.ui.gallery.CustomAdapter;
import com.example.guidesign.ui.gallery.DataModel;
import com.example.guidesign.ui.gallery.showaction;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SlideshowFragment extends Fragment {



    ArrayList<datamodel> datamodels;

    ListView lv;
    String act1,act2,act3,act4,act5,act6,act7,act8;
    String username;
    String userName;
    private static customadapter adapter;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        lv = (ListView)root.findViewById(R.id.listView2);
        username = this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("Name", "");

        initView(root);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                hidire fragment = new hidire();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();



            }
        });

        return root;
    }
    private void initView(View v){

        showList();

    }
    private void showList(){
        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://140.116.70.173/AndroidFileUpload/act.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    act1 = "尚未錄製";
                    act2 = "尚未錄製";
                    act3 = "尚未錄製";
                    act4 = "尚未錄製";
                    act5 = "尚未錄製";
                    act6 = "尚未錄製";
                    act7 = "尚未錄製";
                    act8 = "尚未錄製";

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
                            datamodels= new ArrayList<>();
                            datamodels.add(new datamodel("動作一","1",act1));
                            datamodels.add(new datamodel("動作二","2", act2));
                            datamodels.add(new datamodel("動作三","3", act3));
                            datamodels.add(new datamodel("動作四","4",act4));
                            datamodels.add(new datamodel("動作五","5",act5));
                            datamodels.add(new datamodel("動作六","6", act6));
                            datamodels.add(new datamodel("動作七","7", act7));
                            datamodels.add(new datamodel("動作八","8", act8));
                            adapter= new customadapter(datamodels,getContext());

                            lv.setAdapter(adapter);


                        }



                    } catch (JSONException e){
                        act1 = "尚未錄製";
                        act2 = "尚未錄製";
                        act3 = "尚未錄製";
                        act4 = "尚未錄製";
                        act5 = "尚未錄製";
                        act6 = "尚未錄製";
                        act7 = "尚未錄製";
                        act8 = "尚未錄製";
                        datamodels= new ArrayList<>();
                        datamodels.add(new datamodel("動作一","1",act1));
                        datamodels.add(new datamodel("動作二","2", act2));
                        datamodels.add(new datamodel("動作三","3", act3));
                        datamodels.add(new datamodel("動作四","4",act4));
                        datamodels.add(new datamodel("動作五","5",act5));
                        datamodels.add(new datamodel("動作六","6", act6));
                        datamodels.add(new datamodel("動作七","7", act7));
                        datamodels.add(new datamodel("動作八","8", act8));
                        adapter= new customadapter(datamodels,getContext());

                        lv.setAdapter(adapter);
                    }

                }

            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Log.d("SQL server", "Failed with error msg:\t" + error.getMessage());
            Log.d("SQL server", "Error StackTrace: \t" + error.getStackTrace());
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            userName = username;
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", userName);
            return params;
        }

    };
        Handler.getInstance(getContext()).addToRequestQue(stringRequest);

}
}