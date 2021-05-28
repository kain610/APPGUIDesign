package com.example.guidesign;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guidesign.ui.gallery.CustomAdapter;
import com.example.guidesign.ui.gallery.DataModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private String username, password;
    private String usr1, pwd1,usr2,coin,day,coin1;
    private TextView title,cc,dd;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setItemIconTintList(null);










        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });




        // Passing each menu ID as a set of Ids because each
        // 設定側邊欄啟用的按鈕
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_tools,R.id.nav_problem)
                .setDrawerLayout(drawer)
                .build();
        //設定layout容器
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //取得目前是誰登入
        usr1 = getSharedPreferences("data", MODE_PRIVATE).getString("username1", "");
        pwd1 = getSharedPreferences("data", MODE_PRIVATE).getString("password1", "");

        username = usr1;
        password = pwd1;
        showList();


    }
    private void showList(){
        //取得錄製紀錄

        Log.d("SQL server","start show list");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://140.116.70.157/AndroidFileUpload/coin.php",
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

                                    coin = patientObj.getString("Coins");
                                    cc = findViewById(R.id.coins);
                                    cc.setText(coin);


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

        Handler.getInstance(getApplicationContext()).addToRequestQue(stringRequest);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences.Editor editor=getSharedPreferences("data",0).edit();
        editor.remove("Coins");
        editor.putString("Coins",coin);
        editor.commit();
        //
         usr2 = getSharedPreferences("data", MODE_PRIVATE).getString("Name", "");

         day = getSharedPreferences("data", MODE_PRIVATE).getString("Days", "");

        getMenuInflater().inflate(R.menu.main, menu);

        title = (TextView)findViewById(R.id.username);


        dd = findViewById(R.id.days);
        title.setText( "歡迎!"+usr2);


        dd.setText( "已經連續登入"+day+"天");

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        showList();

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
