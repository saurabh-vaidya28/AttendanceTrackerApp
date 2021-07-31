package com.example.attendancetracker.HOD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.attendancetracker.StartScreen_mainactivity.MainActivity;
import com.example.attendancetracker.R;
import com.google.firebase.auth.FirebaseAuth;

public class hod_home extends AppCompatActivity implements View.OnClickListener{

    Button _logoutHOD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_home);
        setTitle("HOD");
        _logoutHOD=findViewById(R.id.logoutHOD);

        _logoutHOD.setOnClickListener(this);
    }

    private void logoutHOD()
    {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(hod_home.this, MainActivity.class));
    }
    @Override
    public void onClick(View view) {
        if(view==_logoutHOD)
        {
            logoutHOD();
        }
    }


}
