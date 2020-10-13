package com.example.guidesign;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.guidesign.ui.gallery.GalleryFragment;
import com.example.guidesign.ui.home.HomeFragment;
import com.example.guidesign.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private String username, password;
    private String usr1, pwd1,usr2;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_tools)
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









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //
         usr2 = getSharedPreferences("data", MODE_PRIVATE).getString("Name", "");
        getMenuInflater().inflate(R.menu.main, menu);

        title = (TextView)findViewById(R.id.username);
        title.setText( "歡迎!"+usr2);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
