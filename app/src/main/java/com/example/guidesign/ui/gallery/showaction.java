package com.example.guidesign.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.guidesign.R;
import com.example.guidesign.deriction;


public class showaction extends Fragment {
  int act;


    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_showaction, container, false);
      ImageView imageView = root.findViewById(R.id.imageView2);
      TextView textView = root.findViewById(R.id.action_name);

       String str = (String)getArguments().get("position");
        if (str == "0"){
          act = 1;
          textView.setText("動作一");
          Glide.with(this).load(R.drawable.action1).into(imageView);
        }
      if (str == "1"){
        act = 2;
        textView.setText("動作二");
        Glide.with(this).load(R.drawable.action2).into(imageView);
      }
      if (str == "2"){
        act = 3;
        textView.setText("動作三");
        Glide.with(this).load(R.drawable.action3).into(imageView);
      }
      if (str == "3"){
        act = 4;
        textView.setText("動作四");
        Glide.with(this).load(R.drawable.action4).into(imageView);
      }
      if (str == "4"){
        act = 5;
        textView.setText("動作五");
        Glide.with(this).load(R.drawable.action5).into(imageView);
      }
      if (str == "5"){
        act = 6;
        textView.setText("動作六");
        Glide.with(this).load(R.drawable.action6).into(imageView);
      }
      if (str == "6"){
        act = 7;
        textView.setText("動作七");
        Glide.with(this).load(R.drawable.action7).into(imageView);
      }
      if (str == "7"){
        act = 8;
        textView.setText("動作八");
        Glide.with(this).load(R.drawable.action8).into(imageView);
      }
      configureImageButton(root);


        return root;
    }

  private void configureImageButton(View root) {
    ImageButton s1 = root.findViewById(R.id.imageButton);
    s1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        deriction fragment = new deriction();
        Bundle bundle = new Bundle();
        bundle.putString("act", String.valueOf(act));
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
  }


}





