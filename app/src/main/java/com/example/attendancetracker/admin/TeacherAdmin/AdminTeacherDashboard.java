package com.example.attendancetracker.admin.TeacherAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.attendancetracker.R;

public class AdminTeacherDashboard extends AppCompatActivity implements View.OnClickListener {


    TextView j,u,p;
    CardView _jcTCard,_ugTCard,_pgTCard;
    LinearLayout _jcTLinear,_ugTLinear,_pgTLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher_dashboard);
        setTitle("Teacher");

        j=findViewById(R.id.junior);
        u=findViewById(R.id.under);
        p=findViewById(R.id.post);

        _jcTCard=findViewById(R.id.jcTCard);
        _ugTCard=findViewById(R.id.ugTCard);
        _pgTCard=findViewById(R.id.pgTCard);

        _jcTLinear=findViewById(R.id.jcTLinear);
        _ugTLinear=findViewById(R.id.ugTLinear);
        _pgTLinear=findViewById(R.id.pgTLinear);

        j.setOnClickListener(this);
        u.setOnClickListener(this);
        p.setOnClickListener(this);

        _jcTCard.setOnClickListener(this);
        _ugTCard.setOnClickListener(this);
        _pgTCard.setOnClickListener(this);

        _jcTLinear.setOnClickListener(this);
        _ugTLinear.setOnClickListener(this);
        _pgTLinear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==j || v==_jcTCard || v==_jcTLinear){
            startActivity(new Intent(this, juniorTeacher.class));
        }
        if(v==u || v==_ugTCard || v==_ugTLinear ){
            startActivity(new Intent(this, ugTeacher.class));
        }
        if(v==p || v==_pgTCard || v==_pgTLinear ){
            startActivity(new Intent(this, pgTeacher.class));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
