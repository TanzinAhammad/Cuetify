package com.example.cuetify4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegistration extends AppCompatActivity implements OnClickListener {
    Spinner sspinnerl;
    Spinner sspinnert;
    Spinner sspinnerh;
    Spinner sspinnerd;
    private EditText studentEmail,studentPassword,studentPhone,studentId,studentName;
    private Button studentRegister;
    DatabaseReference databaseReference;
    //private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_student_registration);
        databaseReference= FirebaseDatabase.getInstance().getReference("Student");
        sspinnerl = (Spinner) findViewById(R.id.spinnerLevel);
        sspinnert = (Spinner) findViewById(R.id.spinnerTerm);
        sspinnerh = (Spinner) findViewById(R.id.spinnerHall);
        sspinnerd = (Spinner) findViewById(R.id.spinnerDepartment);
        String []sLevel={"1","2","3","4"};
        String []sTerm={"1","2"};
        String []sHall={"Bangabandhu Hall","Shaheed Tareq Huda Hall","Dr. Qudrat-E-Khuda Hall","Shahid Mohammad Shah Hall","Sheikh Russel Hall","Sufia Kamal Hall","Begum Shamsun Nahar Khan Hall"};
        String []sDept={"Civil Engineering","Mechanical Engineering","Electrical And Electronics Engineering","Computer Science & Engineering","Electronics & Telecommunication Eng.","Petroleum And Mining Engineering","Mechatronics & Industrial Engineering","Water Resources Engineering","Biomedical Engineering","Disaster Engineering And Management","Architecture","Urban & Regional Planning","Humanities","Physics","Chemistry","Mathematics","Materials Science And Engineering","Nuclear Engineering"};
        ArrayAdapter<String> sadapterl = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sLevel);
        sspinnerl.setAdapter(sadapterl);
        ArrayAdapter<String> sadaptert = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sTerm);
        sspinnert.setAdapter(sadaptert);
        ArrayAdapter<String> sadapterh = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sHall);
        sspinnerh.setAdapter(sadapterh);
        ArrayAdapter<String> sadapterd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sDept);
        sspinnerd.setAdapter(sadapterd);
        mAuth = FirebaseAuth.getInstance();
        //progressBar= findViewById(R.id.studentProgressbar);
        studentName=findViewById(R.id.studentRegistrationName);
        studentId=findViewById(R.id.studentRegistrationId);
        studentPhone=findViewById(R.id.studentRegistrationPhoneNumber);
        studentEmail = findViewById(R.id.studentRegistrationEmail);
        studentPassword = findViewById(R.id.studentRegistrationPassword);
        studentRegister = findViewById(R.id.studentSubmitButton);
        studentRegister.setOnClickListener(this);

    }
    public void onClick(View v)
    {
        userRegister();
    }

    private void userRegister() {
        String email = studentEmail.getText().toString().trim();
        String password = studentPassword.getText().toString().trim();
        if (email.isEmpty()) {
            studentEmail.setError("Enter a valid email address");
            studentEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            studentEmail.setError("Enter a valid email address");
            studentEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            studentPassword.setError("Enter a valid Password");
            studentPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            studentPassword.setError("Minimum length of a password should be 6");
            studentPassword.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Register is Successful", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "User is already Registered", Toast.LENGTH_SHORT);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        String name = studentName.getText().toString().trim(), id = studentId.getText().toString().trim(), phone = studentPhone.getText().toString().trim();
        //String key = databaseReference.push().getKey();
        studentdata student = new studentdata(name, id, phone, email, password);
        char []key= email.toCharArray();
        String emailkey="";
        for (int i = 0; i < email.length(); i++)
        {
            if(key[i]!='.')
            emailkey=emailkey+key[i];
        }
         databaseReference.child(emailkey).setValue(student);
         studentName.setText("");
         studentId.setText("");
         studentPhone.setText("");
         studentEmail.setText("");
         studentPassword.setText("");
    }


}