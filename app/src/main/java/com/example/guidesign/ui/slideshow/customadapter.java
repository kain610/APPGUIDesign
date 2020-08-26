package com.example.guidesign.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.guidesign.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class customadapter extends ArrayAdapter<datamodel> implements View.OnClickListener {

    private ArrayList<datamodel> dataSet;
    Context mContext;
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }
    public customadapter(ArrayList<datamodel> data, Context context) {
        super(context, R.layout.listview2_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        datamodel dataModel=(datamodel)object;

        switch (v.getId())
        {
            case R.id.item_aro:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }

    }

    private int lastPosition = -1;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        datamodel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview2_layout, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_aro);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }




        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtVersion.setText(dataModel.getFeature());




        // Return the completed view to render on screen
        return convertView;
    }




}
