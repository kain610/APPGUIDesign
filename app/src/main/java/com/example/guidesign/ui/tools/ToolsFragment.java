package com.example.guidesign.ui.tools;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.guidesign.history;
import com.example.guidesign.prUpdate;
import com.example.guidesign.ui.slideshow.customadapter;
import com.example.guidesign.ui.slideshow.datamodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ToolsFragment extends Fragment {


    String account,name,phone;
    String username;
    Button btnPrUpdate;
    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        username = this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("username1", "");


        initView(root);
        configureButton(root);




        return root;
    }

    private void configureButton(View root) {
        btnPrUpdate = (Button) root.findViewById(R.id.btn_prchange);
        btnPrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("account",account);
                bundle.putString("name",name);
                bundle.putString("phone",phone);
                prUpdate fragment =  new prUpdate();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void initView(View v) {

        showperson(v);

    }

    private void showperson(final View v) {
        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://140.116.70.157/AndroidFileUpload/person.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("users");
                            Log.d("SQL server",String.valueOf(array.length()));
                            for ( int i = 0; i<array.length();i++){
                                JSONObject patientObj = array.getJSONObject(i);
                                if(patientObj.getString("Account").equals(username) )
                                {
                                    account = patientObj.getString("Account");
                                    name = patientObj.getString("Name");
                                    phone = patientObj.getString("Phone");

                                }



                            }
                           final TextView text_acc = v.findViewById(R.id.account);
                           final TextView text_name = v.findViewById(R.id.name);
                           final TextView text_phone = v.findViewById(R.id.phone);
                           text_acc.setText(account);
                           text_name.setText(name);
                           text_phone.setText(phone);



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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                return params;
            }

        };
        Handler.getInstance(getContext()).addToRequestQue(stringRequest);
    }

}