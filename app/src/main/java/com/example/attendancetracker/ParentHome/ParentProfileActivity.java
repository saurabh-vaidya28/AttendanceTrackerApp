package com.example.attendancetracker.ParentHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ParentProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView _set_nameParent,_set_emailParent,_set_phoneParent,_set_nameStudent,_set_phoneStudent,_set_emailStudent,_set_details;
    private FirebaseAuth pAuthP;
    private DatabaseReference databaseReferenceP,database_emailP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);

        _set_nameParent=(TextView)findViewById(R.id.profile_parentName);
        _set_emailParent=(TextView)findViewById(R.id.profile_parentEmail);
        _set_phoneParent=(TextView)findViewById(R.id.profile_parentPhone);
        _set_nameStudent=(TextView)findViewById(R.id.profile_parent_sName);
        _set_emailStudent=(TextView)findViewById(R.id.profile_parent_sEmail);
        _set_phoneStudent=(TextView)findViewById(R.id.profile_parent_sPhone);
        _set_details=(TextView)findViewById(R.id.profile_parent_add);

        pAuthP = FirebaseAuth.getInstance();
        databaseReferenceP= FirebaseDatabase.getInstance().getReference().child("Parent_Details").child(pAuthP.getUid());
        database_emailP=FirebaseDatabase.getInstance().getReference().child("Users").child(pAuthP.getUid());


        databaseReferenceP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((!dataSnapshot.child("parent_Name").exists()) && (!dataSnapshot.child("parent_Phone").exists()) && (!dataSnapshot.child("student_Name").exists())
                        && (!dataSnapshot.child("student_Email").exists()) && (!dataSnapshot.child("student_Phone").exists())) {
                    _set_nameParent.setText("");
                    _set_phoneParent.setText("");
                    _set_nameStudent.setText("");
                    _set_emailStudent.setText("");
                    _set_phoneStudent.setText("");
                    _set_details.setText("*Note: Details has not been added");
                    return;
                }

                String valueParentName = dataSnapshot.child("parent_Name").getValue().toString();
                _set_nameParent.setText("Name: " + valueParentName);
                String valueParentPhone = dataSnapshot.child("parent_Phone").getValue().toString();
                _set_phoneParent.setText("Phone No.: " + valueParentPhone);
                String valueStudentName = dataSnapshot.child("student_Name").getValue().toString();
                _set_nameStudent.setText("Name: " + valueStudentName);
                String valueStudentEmail = dataSnapshot.child("student_Email").getValue().toString();
                _set_emailStudent.setText("Email-ID: " + valueStudentEmail);
                String valueStudentPhone = dataSnapshot.child("student_Phone").getValue().toString();
                _set_phoneStudent.setText("Phone No: " + valueStudentPhone);
                _set_details.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database_emailP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueEmail=dataSnapshot.child("email").getValue().toString();
                _set_emailParent.setText("Email-ID: "+valueEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        _set_details.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==_set_details)
        {
            if(_set_details.equals("*Note: Details has not been added"))
            {
                startActivity(new Intent(ParentProfileActivity.this,ParentDetailsActivity.class));
            }
            else
            {
                Toast.makeText(ParentProfileActivity.this,"Details has been added already",Toast.LENGTH_LONG).show();
            }

        }
    }
}
