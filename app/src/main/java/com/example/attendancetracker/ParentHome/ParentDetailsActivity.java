package com.example.attendancetracker.ParentHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendancetracker.R;
import com.example.attendancetracker.ui.base.BaseDrawerActivity;
import com.example.attendancetracker.ui.studentdetails.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText _parentName,_parentPhone,_studentPhone,_studentEmail,_studentName;
    private Button _parentAdd;
    private ProgressBar _p_progress;
    private FirebaseAuth firebaseAuthP;
    private DatabaseReference reffP;
    private Parent parentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_details);
        _p_progress=(ProgressBar)findViewById(R.id.p_progress);
        _parentName=(EditText)findViewById(R.id.parent_name);
        _parentPhone=(EditText)findViewById(R.id.parent_phone);
        _studentName=(EditText)findViewById(R.id.student_name_parent);
        _studentEmail=(EditText)findViewById(R.id.student_email_parent);
        _studentPhone=(EditText)findViewById(R.id.student_phone_parent);

        _parentAdd=(Button)findViewById(R.id.parent_save);

        parentDetails=new Parent();
        reffP= FirebaseDatabase.getInstance().getReference().child("Parent_Details");
        firebaseAuthP =FirebaseAuth.getInstance();

        _parentAdd.setOnClickListener(this);
    }

    private void AddDetails_Parent()
    {
        String nameP=_parentName.getText().toString().trim();
        String phoneP=_parentPhone.getText().toString().trim();
        String nameS=_studentName.getText().toString().trim();
        String mobileS=_studentPhone.getText().toString().trim();
        String emailS=_studentEmail.getText().toString().trim();


        if(TextUtils.isEmpty(nameP)){
            _parentName.setError("Please enter your name");
            return;
        }

        if(TextUtils.isEmpty(phoneP)){
            _parentPhone.setError("Please enter your mobile no.");
            return;
        }

        //checking for mobile no.
        if(TextUtils.isEmpty(nameS)){
            _studentName.setError("Please enter your child's name");
            return;
        }

        if(TextUtils.isEmpty(emailS)){
            _studentEmail.setError("Please enter your child's email-id");
            return;
        }

        if(TextUtils.isEmpty(mobileS)){
            _studentPhone.setError("Please enter your child's mobile no.");
            return;
        }

        parentDetails.setParent_Name(_parentName.getText().toString());
        parentDetails.setParent_Phone(_parentPhone.getText().toString());
        parentDetails.setStudent_Name(_studentName.getText().toString());
        parentDetails.setStudent_Email(_studentEmail.getText().toString());
        parentDetails.setStudent_Phone(_studentPhone.getText().toString());

        _p_progress.setVisibility(View.VISIBLE);
        if((!TextUtils.isEmpty(nameP)) && (!TextUtils.isEmpty(phoneP)) && (!TextUtils.isEmpty(mobileS))&& (!TextUtils.isEmpty(nameS))&& (!TextUtils.isEmpty(emailS)))
        {
            reffP.child(firebaseAuthP.getUid()).setValue(parentDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                _p_progress.setVisibility(View.GONE);
                                Toast.makeText(ParentDetailsActivity.this,"Details added successfully",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ParentDetailsActivity.this,ParentProfileActivity.class));
                            } else {
                                _p_progress.setVisibility(View.GONE);
                                Toast.makeText(ParentDetailsActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            _p_progress.setVisibility(View.GONE);
                            Toast.makeText(ParentDetailsActivity.this,"Details has not been added",Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else
        {
            _p_progress.setVisibility(View.GONE);
            Toast.makeText(ParentDetailsActivity.this,"One of the field is empty",Toast.LENGTH_LONG).show();
        }

    }
    private void closeKeyboard()
    {
        View view=this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==_parentAdd)
        {
            AddDetails_Parent();
            closeKeyboard();
        }
    }
}
