package com.example.guidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class remember extends AppCompatActivity {

    private String usr1, pwd1;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);
        title=(TextView) findViewById(R.id.username);
        title.setText("大丈夫生于乱世，当带三尺剑立不世功");
    }
}
