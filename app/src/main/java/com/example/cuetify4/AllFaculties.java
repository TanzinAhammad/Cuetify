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

public class AllFaculties extends AppCompatActivity {
    private ListView listView;
    DatabaseReference databaseReference;
    private List<FacultyData>facultyList;
    private FacultyAdapter facultyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_faculties);
        databaseReference = FirebaseDatabase.getInstance().getReference("Faculty");
        facultyList = new ArrayList<>();
        facultyAdapter = new FacultyAdapter(AllFaculties.this,facultyList);

        listView=findViewById(R.id.listViewId);
    }

    @Override
    protected void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                facultyList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    FacultyData Faculty = dataSnapshot1.getValue(FacultyData.class);
                    facultyList.add(Faculty);
                }
                listView.setAdapter(facultyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
