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

public class CrAdapter extends ArrayAdapter<CrData> {
    private Activity context;
    private List<CrData> crdataList;

    public CrAdapter(@NonNull Activity context, List<CrData> crdataList) {
        super(context,R.layout.sample_layout,crdataList);
        this.context= context;

        this.crdataList = crdataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.sample_layout,null,true);
        CrData Cr= crdataList.get(position);
        TextView t1=view.findViewById(R.id.nameTextViewId);
        TextView t2=view.findViewById(R.id.IdTextViewId);
        t1.setText("Name : "+Cr.getName());
        t2.setText("Id : "+Cr.getId());
        return view;
    }
}
