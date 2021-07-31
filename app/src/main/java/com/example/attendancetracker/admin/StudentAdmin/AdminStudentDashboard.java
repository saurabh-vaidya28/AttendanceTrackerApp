package com.example.attendancetracker.admin.StudentAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.attendancetracker.R;

public class AdminStudentDashboard extends AppCompatActivity implements View.OnClickListener {

    TextView j,u,p;
    CardView _jcCard,_ugCard,_pgCard;
    LinearLayout _jcLinear,_ugLinear,_pgLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_dashboard);
        setTitle("Student");

        j=findViewById(R.id.junior);
        u=findViewById(R.id.under);
        p=findViewById(R.id.post);

        _jcCard=findViewById(R.id.jcCard);
        _ugCard=findViewById(R.id.ugCard);
        _pgCard=findViewById(R.id.pgCard);

        _jcLinear=findViewById(R.id.jcLinear);
        _ugLinear=findViewById(R.id.ugLinear);
        _pgLinear=findViewById(R.id.pgLinear);

        _jcCard.setOnClickListener(this);
        _ugCard.setOnClickListener(this);
        _pgCard.setOnClickListener(this);

        j.setOnClickListener(this);
        u.setOnClickListener(this);
        p.setOnClickListener(this);

        _jcLinear.setOnClickListener(this);
        _ugLinear.setOnClickListener(this);
        _pgLinear.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==j || v==_jcCard || v==_jcLinear){
            startActivity(new Intent(this, j_department.class));
        }
        if(v==u || v==_ugCard || v==_ugLinear){
            startActivity(new Intent(this, ug_department.class));
        }
        if(v==p || v==_pgCard || v==_pgLinear){
            startActivity(new Intent(this, p_department.class));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
