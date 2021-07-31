package com.example.attendancetracker.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.attendancetracker.R;
import com.example.attendancetracker.admin.StudentAdmin.AdminStudentDashboard;
import com.example.attendancetracker.admin.TeacherAdmin.AdminTeacherDashboard;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView studentDash,teacherDash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Admin");

        studentDash=(CardView)findViewById(R.id.studentCard);
        teacherDash=(CardView)findViewById(R.id.teacherCard);


        studentDash.setOnClickListener(this);
        teacherDash.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==studentDash)
        {
            startActivity(new Intent(AdminActivity.this, AdminStudentDashboard.class));
        }
        if(v==teacherDash)
        {
            startActivity(new Intent(AdminActivity.this, AdminTeacherDashboard.class));
        }
    }
}
