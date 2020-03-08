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
public class CrRegistration extends AppCompatActivity implements OnClickListener {
    Spinner cspinnerl;
    Spinner cspinnert;
    Spinner cspinnerh;
    Spinner cspinnerd;
    private EditText crEmail,crPassword,crPhone,crId,crName;
    private Button crRegister;
    DatabaseReference databaseReference;
    //private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_cr_registration);
        databaseReference= FirebaseDatabase.getInstance().getReference("Cr");
        cspinnerl = (Spinner) findViewById(R.id.crSpinnerLevel);
        cspinnert = (Spinner) findViewById(R.id.crSpinnerTerm);
        cspinnerh = (Spinner) findViewById(R.id.crSpinnerHall);
        cspinnerd = (Spinner) findViewById(R.id.crSpinnerDepartment);
        String []cLevel={"1","2","3","4"};
        String []cTerm={"1","2"};
        String []cHall={"Bangabandhu Hall","Shaheed Tareq Huda Hall","Dr. Qudrat-E-Khuda Hall","Shahid Mohammad Shah Hall","Sheikh Russel Hall","Sufia Kamal Hall","Begum Shamsun Nahar Khan Hall"};
        String []cDept={"Civil Engineering","Mechanical Engineering","Electrical And Electronics Engineering","Computer Science & Engineering","Electronics & Telecommunication Eng.","Petroleum And Mining Engineering","Mechatronics & Industrial Engineering","Water Resources Engineering","Biomedical Engineering","Disaster Engineering And Management","Architecture","Urban & Regional Planning","Humanities","Physics","Chemistry","Mathematics","Materials Science And Engineering","Nuclear Engineering"};
        ArrayAdapter<String> cadapterl = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cLevel);
        cspinnerl.setAdapter(cadapterl);
        ArrayAdapter<String> cadaptert = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cTerm);
        cspinnert.setAdapter(cadaptert);
        ArrayAdapter<String> cadapterh = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cHall);
        cspinnerh.setAdapter(cadapterh);
        ArrayAdapter<String> cadapterd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cDept);
        cspinnerd.setAdapter(cadapterd);
        mAuth = FirebaseAuth.getInstance();
        //progressBar= findViewById(R.id.studentProgressbar);
        crName=findViewById(R.id.crRegistrationName);
        crId=findViewById(R.id.crRegistrationId);
        crPhone=findViewById(R.id.crRegistrationPhoneNumber);
        crEmail = findViewById(R.id.crRegistrationEmail);
        crPassword = findViewById(R.id.crRegistrationPassword);
        crRegister = findViewById(R.id.crSubmitButton);
        crRegister.setOnClickListener(this);

    }
    public void onClick(View v)
    {
        userRegister();
    }

    private void userRegister() {
        String email = crEmail.getText().toString().trim();
        String password = crPassword.getText().toString().trim();
        if (email.isEmpty()) {
            crEmail.setError("Enter a valid email address");
            crEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            crEmail.setError("Enter a valid email address");
            crEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            crPassword.setError("Enter a valid Password");
            crPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            crPassword.setError("Minimum length of a password should be 6");
            crPassword.requestFocus();
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

        String name = crName.getText().toString().trim(), id = crId.getText().toString().trim(), phone = crPhone.getText().toString().trim();
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
        crName.setText("");
        crId.setText("");
        crPhone.setText("");
        crEmail.setText("");
        crPassword.setText("");
    }

}
