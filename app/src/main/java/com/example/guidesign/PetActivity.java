package com.example.guidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class PetActivity extends AppCompatActivity {
    private ImageButton btnrecord,btnrecord1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        initView();
        initListener();
    }

    private void initView(){
        btnrecord =  findViewById(R.id.btn_dog);
        btnrecord1 = findViewById(R.id.btn_chick);


    }
    private void initListener(){
         btnrecord.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View arg0, MotionEvent motionEvent) {
                Intent intent = new Intent(PetActivity.this, register.class);
                Bundle bundle = new Bundle();
                bundle.putString("pet","1");
                intent.putExtras(bundle);
                startActivity(intent);

                return false;
            }
        });
        btnrecord1.setOnTouchListener(new Button.OnTouchListener(){
            @Override
            public boolean onTouch(View arg0, MotionEvent motionEvent) {
                Intent intent = new Intent(PetActivity.this, register.class);
                Bundle bundle = new Bundle();
                bundle.putString("pet","2");
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
        });


    }
}