package com.example.guidesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guidesign.ui.home.HomeFragment;

public class register extends AppCompatActivity {

    private String userName,pwd,checkpwd,phone ,status;
    private EditText eduser,edpwd,edcheckpwd,edphone;
    public   int local;
    private Button sumbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sumbit = findViewById(R.id.regist);
        Spinner spinner = findViewById(R.id.spinner);
        final String[] place = {"成醫復健部","成醫老年科"};
        ArrayAdapter<String> placeList = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                place);spinner.setAdapter(placeList);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               local = position+1;
            }

                       @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        init();
        initListener();
    }



    private void init() {
        eduser = (EditText)findViewById(R.id.user);
        edpwd = (EditText)findViewById(R.id.pwd);
        edcheckpwd = (EditText)findViewById(R.id.checkpwd);
        edphone = (EditText)findViewById(R.id.phone);

    }
    private void initListener() {

        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = eduser.getText().toString();
                pwd = edpwd.getText().toString();
                checkpwd = edcheckpwd.getText().toString();
                phone = edphone.getText().toString();
                status = String.valueOf(local);
                AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                builder.setMessage(status).setTitle("提醒")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // do nothing
                            }
                        }
                        );
                builder.show();
            }
        });
    }


}