package com.example.guidesign;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import static android.content.Context.MODE_PRIVATE;


public class ProblemFragment extends Fragment {

    public ProblemFragment() {
        // Required empty public constructor
    }
    private static int act;
    private static String username, password, direction,act1;
    private ImageButton btnRecord,btnRecord1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_problem, container, false);
        String usr1 =this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("username1", "");
        String pwd1 =this.getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("password1", "");
        username = usr1;
        password = pwd1;



        initView(root);






        return  root;
    }

    private void initView(View root) {
        btnRecord =  root.findViewById(R.id.btn_chat);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                bundle.putString("password",password);
                ChatFragment fragment =  new ChatFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnRecord1 = root.findViewById(R.id.btn_buy);
        btnRecord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                bundle.putString("password",password);
                ShopFragment fragment =  new ShopFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}