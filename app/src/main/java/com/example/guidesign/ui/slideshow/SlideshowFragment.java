package com.example.guidesign.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.guidesign.R;
import com.example.guidesign.hidire;
import com.example.guidesign.history;
import com.example.guidesign.ui.gallery.CustomAdapter;
import com.example.guidesign.ui.gallery.DataModel;
import com.example.guidesign.ui.gallery.showaction;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {



    ArrayList<datamodel> datamodels;

    ListView lv;
    private static customadapter adapter;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        lv = (ListView)root.findViewById(R.id.listView2);
        datamodels= new ArrayList<>();
        datamodels.add(new datamodel("動作一","1","2020-01-01"));
        datamodels.add(new datamodel("動作二","2", "2020-01-01"));
        datamodels.add(new datamodel("動作三","3", "2020-01-01"));
        datamodels.add(new datamodel("動作四","4","2020-01-01"));
        datamodels.add(new datamodel("動作五","5","2020-01-01"));
        datamodels.add(new datamodel("動作六","6", "2020-01-01"));
        datamodels.add(new datamodel("動作七","7", "2020-01-01"));
        datamodels.add(new datamodel("動作八","8", "2020-01-01"));
        adapter= new customadapter(datamodels,getContext());

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("position", String.valueOf(position));
                hidire fragment = new hidire();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();



            }
        });

        return root;
    }
}