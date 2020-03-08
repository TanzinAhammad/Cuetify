package com.example.cuetify4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPanel extends AppCompatActivity implements View.OnClickListener{
    private Button studentbutton,facultybutton,crbutton,signupbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_login_panel);
        studentbutton=findViewById(R.id.loginPanelStudent);
        signupbutton=findViewById(R.id.signUpButton);
        facultybutton=findViewById(R.id.loginPanelFaculty);
        crbutton=findViewById(R.id.loginPanelCr);
        facultybutton.setOnClickListener(this);
        studentbutton.setOnClickListener(this);
        signupbutton.setOnClickListener(this);
        crbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.loginPanelStudent:
                Intent intent=new Intent(getApplicationContext(),StudentSignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.signUpButton:
                Intent intent1=new Intent(getApplicationContext(),Registration.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                        break;
            case R.id.loginPanelFaculty:
                Intent intent2=new Intent(getApplicationContext(),FacultySignIn.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;

                case R.id.loginPanelCr:
            Intent intent3=new Intent(getApplicationContext(),CrSignIn.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent3);
            break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}
