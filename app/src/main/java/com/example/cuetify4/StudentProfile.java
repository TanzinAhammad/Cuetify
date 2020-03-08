package com.example.cuetify4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StudentProfile extends AppCompatActivity {

    TextView mNameView;
    DatabaseReference databaseReference;
    FirebaseAuth mAut;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        mNameView=(TextView)findViewById(R.id.nameId);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
        char []key= email.toCharArray();
        String emailkey="";
        for (int i = 0; i < email.length(); i++)
        {
            if(key[i]!='.')
                emailkey=emailkey+key[i];
        }
            String uid = user.getUid();
            databaseReference=FirebaseDatabase.getInstance().getReference().child("Student").child(emailkey);
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              String name=dataSnapshot.child("name").getValue().toString();
              String id=dataSnapshot.child("id").getValue().toString();
              String email=dataSnapshot.child("email").getValue().toString();
              String password=dataSnapshot.child("password").getValue().toString();
              String phone=dataSnapshot.child("phone").getValue().toString();
              mNameView.setText("  Name : "+name+"\n"+"  Id : "+id+"\n  Email : "+email+"\n  Password : "+password+"\n  Phone : "+phone);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }



}
