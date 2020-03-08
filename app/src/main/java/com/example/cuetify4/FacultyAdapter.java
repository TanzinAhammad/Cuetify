package com.example.cuetify4;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FacultyAdapter extends ArrayAdapter<FacultyData> {
    private Activity context;
    private List<FacultyData> facultydataList;

    public FacultyAdapter(@NonNull Activity context, List<FacultyData> facultydataList) {
        super(context,R.layout.sample_layout,facultydataList);
        this.context= context;

        this.facultydataList = facultydataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.sample_layout,null,true);
        FacultyData Faculty= facultydataList.get(position);
        TextView t1=view.findViewById(R.id.nameTextViewId);
        TextView t2=view.findViewById(R.id.IdTextViewId);
        t1.setText("Name : "+Faculty.getName());
        t2.setText("Id : "+Faculty.getId());
        return view;
    }
}
