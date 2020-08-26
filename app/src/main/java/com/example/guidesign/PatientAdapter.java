package com.example.guidesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import com.example.guidesign.Patient;
import com.example.guidesign.R;

public class PatientAdapter extends ArrayAdapter<Patient> {

    private List<Patient> patientList;
    private Context mCtx;

    public PatientAdapter(List<Patient> P, Context c)
    {
        super(c, R.layout.listview_layout,P);
        this.patientList = P;
        this.mCtx=c;
    }


    public View getView(int position, View converView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.listview_layout,null,true);
        TextView nama = (TextView) view.findViewById(R.id.name);
        TextView complete_score = (TextView) view.findViewById(R.id.complete_score);
        TextView fluency_score = (TextView) view.findViewById(R.id.fluency_score);
        TextView time = (TextView) view.findViewById(R.id.time);

        Patient patient = patientList.get(position);
        nama.setText(patient.getNama());
        complete_score.setText(patient.getCompleteScore());
        fluency_score.setText(patient.getFluencyScore());
        time.setText(patient.getTime());

        return view;
    }
}
