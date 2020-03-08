package com.example.cuetify4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.session.PlaybackState;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllCrs extends AppCompatActivity {
    private ListView listView;
    DatabaseReference databaseReference;
    private List<CrData>crList;
    private CrAdapter crAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_crs);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cr");
        crList = new ArrayList<>();
        crAdapter = new CrAdapter(AllCrs.this,crList);

        listView=findViewById(R.id.listViewId);
    }

    @Override
    protected void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                crList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    CrData cr = dataSnapshot1.getValue(CrData.class);
                    crList.add(cr);
                }
                listView.setAdapter(crAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
