package com.example.attendancetracker.ParentHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.attendancetracker.StartScreen_mainactivity.MainActivity;
import com.example.attendancetracker.R;
import com.google.firebase.auth.FirebaseAuth;

public class parent_home extends AppCompatActivity implements View.OnClickListener {
    CardView _detailsParent,_profileParent,_trackParent,_logoutParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);
        setTitle("Parent");
        _detailsParent=(CardView)findViewById(R.id.details_parent);
        _profileParent=(CardView)findViewById(R.id.profile_parent);
        _trackParent=(CardView)findViewById(R.id.track_parent);
        _logoutParent=(CardView)findViewById(R.id.parentLogout);

        _detailsParent.setOnClickListener(this);
        _profileParent.setOnClickListener(this);
        _trackParent.setOnClickListener(this);
        _logoutParent.setOnClickListener(this);
    }

    private void logoutParent()
    {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(parent_home.this, MainActivity.class));
    }
    @Override
    public void onClick(View view) {
        if(view==_detailsParent)
        {
            startActivity(new Intent(parent_home.this,ParentDetailsActivity.class));
        }

        if(view==_profileParent)
        {
            startActivity(new Intent(parent_home.this,ParentProfileActivity.class));
        }

        if(view==_trackParent)
        {
            startActivity(new Intent(parent_home.this,ParentTrackActivity.class));
        }
        if(view==_logoutParent)
        {
            logoutParent();
        }
    }
}
