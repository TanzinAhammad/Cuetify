package com.example.cuetify4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentSignIn extends AppCompatActivity implements View.OnClickListener {
    private EditText signInEmailEditText,signInPasswordEditText;
    private Button signInButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_in);
        mAuth=FirebaseAuth.getInstance();
        signInEmailEditText= findViewById(R.id.studentSignInEmail);
        signInPasswordEditText=findViewById(R.id.studentSignInPassword);
        signInButton =findViewById(R.id.studentSignInButton);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        studentLogIn();
    }

    private void studentLogIn() {
        String email= signInEmailEditText.getText().toString().trim();
        String password=signInPasswordEditText.getText().toString().trim();
        if(email.isEmpty())
        {
            signInEmailEditText.setError("Enter a valid email address");
            signInEmailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmailEditText.setError("Enter a valid email address");
            signInEmailEditText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            signInPasswordEditText.setError("Enter a valid Password");
            signInPasswordEditText.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            signInPasswordEditText.setError("Minimum length of a password should be 6");
            signInPasswordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful())
                 {
                     Intent intent=new Intent(getApplicationContext(),StudentPanel.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);
                     Toast.makeText(getApplicationContext(),"Successfully logged In",Toast.LENGTH_SHORT).show();

                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                 }
            }
        });


    }
}
