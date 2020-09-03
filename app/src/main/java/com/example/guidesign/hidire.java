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

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class hidire extends Fragment {


    private Button btnRecord;
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
            act = 1;                    }
        if (str == 1){
            act = 2;
                    }
        if (str == 2){
            act = 3;
        }
        if (str == 3){
            act = 4;
        }
        if (str == 4){
            act = 5;
        }
        if (str == 5){
            act = 6;
        }
        if (str == 6){
            act = 7;
        }
        if (str == 7){
            act = 8;
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
