package com.example.cuetify4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);
        findViewById(R.id.registrationPanelStudent).setOnClickListener(this);
        findViewById(R.id.registrationPanelCr).setOnClickListener(this);
        findViewById(R.id.registrationPanelFaculty).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registrationPanelStudent :
                startActivity(new Intent(this,StudentRegistration.class).putExtra("registrationType","student"));
                break;
            case R.id.registrationPanelCr :
                startActivity(new Intent(this,CrRegistration.class).putExtra("registrationType","cr"));
                break;
            case R.id.registrationPanelFaculty :
                startActivity(new Intent(this,FacultyRegistration.class).putExtra("registrationType","faculty"));
                break;
        }
    }

}
