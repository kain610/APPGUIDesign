package com.example.guidesign;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.guidesign.ui.slideshow.datamodel;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class hidire extends Fragment {


    private Button btnRecord;
    private static String act1;
    private static int act;
    private static String username, password, direction;
    private RadioButton lefthand;
    private RadioButton righthand;
    private RadioGroup radioGroup;

    public hidire() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_hidire, container, false);
        Bundle bundle = getArguments();

        int str =bundle.getInt("position") ;
        if (str == 0){
            act1 = "摸同側耳朵";
            act=1;
        }
        if (str == 1){
            act1 = "摸對側膝蓋";
            act=2;
                    }
        if (str == 2){
            act1 = "摸屁股";
            act=3;
        }
        if (str == 3){
            act1 = "手向前抬-手肘伸直";
            act=4;
        }
        if (str == 4){
            act1 = "翻掌-手肘彎曲";
            act=5;
        }
        if (str == 5){
            act1 = "手向側邊平舉-手肘伸直";
            act=6;
        }
        if (str == 6){
            act1 = "手向上抬-手肘伸直";
            act=7;
        }
        if (str == 7){
            act1 = "翻掌-手肘伸直";
            act=8;
        }
        lefthand = root.findViewById(R.id.lefthand);
        if(lefthand.isChecked()){

            direction="Left";
        }


        radioGroup = (RadioGroup)root.findViewById(R.id.which_hand);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int arg1) {
                righthand = group.findViewById(R.id.righthand);
                lefthand = group.findViewById(R.id.lefthand);
                if (righthand.isChecked()){

                    direction="Right";
                }
                if(lefthand.isChecked()){

                    direction="Left";
                }

            }
        });




        String usr1 =this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("username1", "");
        String pwd1 =this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("password1", "");
        username = usr1;
        password = pwd1;

        configureButton(root);
        return root;
    }

    private void configureButton(View root) {
        btnRecord = (Button) root.findViewById(R.id.deri_button);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("act", act);
                bundle.putString("act1", act1);
                bundle.putString("username",username);
                bundle.putString("password",password);
                bundle.putString("direction",direction);
                history fragment =  new history();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

}
