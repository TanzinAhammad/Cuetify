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

public class StudentAdapter extends ArrayAdapter<studentdata> {
       private Activity context;
       private List<studentdata> studentdataList;

    public StudentAdapter(@NonNull Activity context, List<studentdata> studentdataList) {
        super(context,R.layout.sample_layout,studentdataList);
        this.context= context;

        this.studentdataList = studentdataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.sample_layout,null,true);
        studentdata Student= studentdataList.get(position);
        TextView t1=view.findViewById(R.id.nameTextViewId);
        TextView t2=view.findViewById(R.id.IdTextViewId);
        t1.setText("Name : "+Student.getName());
        t2.setText("Id : "+Student.getId());
        return view;
    }
}
