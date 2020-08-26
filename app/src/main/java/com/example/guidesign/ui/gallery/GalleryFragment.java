package com.example.guidesign.ui.gallery;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.guidesign.R;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    View v;
    ArrayList<DataModel> dataModels;

    ListView lv;
    private static CustomAdapter adapter;


    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);

        v = inflater.inflate(R.layout.fragment_gallery, container,false);

        lv = (ListView)v.findViewById(R.id.listView1);
        dataModels= new ArrayList<>();
        dataModels.add(new DataModel("動作一","1","2020-01-01"));
        dataModels.add(new DataModel("動作二","2", "2020-01-01"));
        dataModels.add(new DataModel("動作三","3", "2020-01-01"));
        dataModels.add(new DataModel("動作四","4","2020-01-01"));
        dataModels.add(new DataModel("動作五","5","2020-01-01"));
        dataModels.add(new DataModel("動作六","6", "2020-01-01"));
        dataModels.add(new DataModel("動作七","7", "2020-01-01"));
        dataModels.add(new DataModel("動作八","8", "2020-01-01"));
        adapter= new CustomAdapter(dataModels,getContext());

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("position", String.valueOf(position));
                showaction fragment = new showaction();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();



            }
        });





        return v;
    }


}