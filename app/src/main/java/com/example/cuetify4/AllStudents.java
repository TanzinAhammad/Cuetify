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

public class AllStudents extends AppCompatActivity {
    private ListView listView;
    DatabaseReference databaseReference;
    private List<studentdata>studentList;
    private StudentAdapter studnetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        studentList = new ArrayList<>();
        studnetAdapter = new StudentAdapter(AllStudents.this,studentList);

        listView=findViewById(R.id.listViewId);
    }

    @Override
    protected void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    studentdata student = dataSnapshot1.getValue(studentdata.class);
                    studentList.add(student);
                }
                listView.setAdapter(studnetAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
