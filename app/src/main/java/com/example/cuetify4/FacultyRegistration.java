package com.example.cuetify4;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

public class FacultyRegistration extends AppCompatActivity implements View.OnClickListener {

    Spinner fspinnerdt;
    Spinner fspinnerdg;
    private EditText facultyEmail,facultyPassword,facultyPhone,facultyId,facultyName;
    private Button facultyRegister;
    DatabaseReference databaseReference;
    //private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_registration);
        databaseReference= FirebaseDatabase.getInstance().getReference("Faculty");
        fspinnerdt = (Spinner) findViewById(R.id.facultySpinnerDepartment);
        fspinnerdg = (Spinner) findViewById(R.id.facultySpinnerDesignation);
        String []fDept={"Civil Engineering","Mechanical Engineering","Electrical And Electronics Engineering","Computer Science & Engineering","Electronics & Telecommunication Eng.","Petroleum And Mining Engineering","Mechatronics & Industrial Engineering","Water Resources Engineering","Biomedical Engineering","Disaster Engineering And Management","Architecture","Urban & Regional Planning","Humanities","Physics","Chemistry","Mathematics","Materials Science And Engineering","Nuclear Engineering"};
        String []Designation={"Lecturer","Assistant Professor","Associate Professor","Professor"};
        ArrayAdapter<String> fadapterdt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,fDept);
        fspinnerdt.setAdapter(fadapterdt);
        ArrayAdapter<String> fadapterdg = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,Designation);
        fspinnerdg.setAdapter(fadapterdg);
        mAuth = FirebaseAuth.getInstance();
        facultyName=findViewById(R.id.facultyRegistrationName);
        facultyId=findViewById(R.id.facultyRegistrationId);
        facultyPhone=findViewById(R.id.facultyRegistrationPhoneNumber);
        facultyEmail = findViewById(R.id.facultyRegistrationEmail);
        facultyPassword = findViewById(R.id.facultyRegistrationPassword);
        facultyRegister = findViewById(R.id.facultySubmitButton);
        facultyRegister.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        userRegister();
    }

    private void userRegister() {
        String email = facultyEmail.getText().toString().trim();
        String password = facultyPassword.getText().toString().trim();
        if (email.isEmpty()) {
            facultyEmail.setError("Enter a valid email address");
            facultyEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            facultyEmail.setError("Enter a valid email address");
            facultyEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            facultyPassword.setError("Enter a valid Password");
            facultyPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            facultyPassword.setError("Minimum length of a password should be 6");
            facultyPassword.requestFocus();
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

        String name = facultyName.getText().toString().trim(), id = facultyId.getText().toString().trim(), phone = facultyPhone.getText().toString().trim();
        //String key = databaseReference.push().getKey();
        FacultyData faculty = new FacultyData(name, id, phone, email, password);
        char []key= email.toCharArray();
        String emailkey="";
        for (int i = 0; i < email.length(); i++)
        {
            if(key[i]!='.')
                emailkey=emailkey+key[i];
        }
        databaseReference.child(emailkey).setValue(faculty);
        facultyName.setText("");
        facultyId.setText("");
        facultyPhone.setText("");
        facultyEmail.setText("");
        facultyPassword.setText("");
    }

}
