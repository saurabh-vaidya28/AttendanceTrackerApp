package com.example.attendancetracker.ui.teacherprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.TeacherHomeActivity;
import com.example.attendancetracker.ui.base.BaseDrawerActivity;
import com.example.attendancetracker.ui.teacherdetails.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText _teacherNameU,_teacherEmpCodeU,_teacherPhoneU;
    private Button _teacherAddU;
    private ProgressBar _t_progressU;
    private FirebaseAuth firebaseAuthTU;
    private DatabaseReference reffTUID,reffTAllU,reffTU;
    private Teacher teacherDetailsU;

    public TextView _collegeU,_departmentU,_yearU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setTitle("Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _teacherNameU=findViewById(R.id.t_nameU);
        _teacherEmpCodeU=findViewById(R.id.t_IDU);
        _teacherPhoneU=findViewById(R.id.t_phoneU);
        _t_progressU=findViewById(R.id.t_progressU);
        _teacherAddU=findViewById(R.id.t_saveU);

        _collegeU =findViewById(R.id.collegeU);
        _departmentU = findViewById(R.id.departmentU);
        _yearU =findViewById(R.id.yearU);

        teacherDetailsU=new Teacher();
        firebaseAuthTU =FirebaseAuth.getInstance();
        reffTUID= FirebaseDatabase.getInstance().getReference().child("Teacher_Details").child(firebaseAuthTU.getUid());
        reffTAllU= FirebaseDatabase.getInstance().getReference().child("Teacher_Details");
        reffTU=FirebaseDatabase.getInstance().getReference().child("Organised_Teacher_Details");

        _teacherAddU.setOnClickListener(this);

        reffTUID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String college_teacher = dataSnapshot.child("college").getValue().toString();
                _collegeU.setText(college_teacher);
                String department_teacher = dataSnapshot.child("dept").getValue().toString();
                _departmentU.setText(department_teacher);
                String year_teacher = dataSnapshot.child("year").getValue().toString();
                _yearU.setText(year_teacher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addDetailsU()
    {
        String nameT=_teacherNameU.getText().toString().trim();
        String empCode=_teacherEmpCodeU.getText().toString().trim();
        String mobileT=_teacherPhoneU.getText().toString().trim();

        if(TextUtils.isEmpty(empCode)){
            Toast.makeText(getApplicationContext(),"Please enter valid employee id",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(nameT)){
            Toast.makeText(getApplicationContext(),"Please enter your name",Toast.LENGTH_LONG).show();
            return;
        }
        //checking for mobile no.
        if(TextUtils.isEmpty(mobileT)){
            Toast.makeText(getApplicationContext(),"Please enter mobile no.",Toast.LENGTH_LONG).show();
            return;
        }
        if((mobileT.length() > 10) || (mobileT.length() < 10)){
            Toast.makeText(getApplicationContext(),"Mobile no. must be of 10 digits",Toast.LENGTH_LONG).show();
            return;
        }

        teacherDetailsU.setName_of_Teacher(_teacherNameU.getText().toString().trim());
        teacherDetailsU.setEmpCode_of_Teacher(_teacherEmpCodeU.getText().toString().trim());
        teacherDetailsU.setPhone_of_Teacher(_teacherPhoneU.getText().toString().trim());
        teacherDetailsU.setCollege(_collegeU.getText().toString().trim());
        teacherDetailsU.setDept(_departmentU.getText().toString().trim());
        teacherDetailsU.setYear(_yearU.getText().toString().trim());

        _t_progressU.setVisibility(View.VISIBLE);
        if((!TextUtils.isEmpty(nameT)) && (!TextUtils.isEmpty(empCode)) && (!TextUtils.isEmpty(mobileT)))
        {
            reffTU.child(_collegeU.getText().toString().trim()).child(_departmentU.getText().toString().trim())
                    .child(_yearU.getText().toString().trim()).child(firebaseAuthTU.getUid()).setValue(teacherDetailsU);
            reffTAllU.child(firebaseAuthTU.getUid()).setValue(teacherDetailsU)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                _t_progressU.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Details updated successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(UpdateProfileActivity.this, TeacherHomeActivity.class));
//                                if (getActivity() instanceof BaseDrawerActivity){
//                                    ((BaseDrawerActivity)getActivity()).setDrawerEnabled(true);
//                                    ((BaseDrawerActivity)getActivity()).displaySelectedScreen(R.id.nav_teacherhome);
//                                }
                            } else {
                                Toast.makeText(UpdateProfileActivity.this,"Error Occured, Please try again later",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }

    }

    @Override
    public void onClick(View view) {
        if(view==_teacherAddU)
        {
            addDetailsU();
            closeKeyboard();
        }

    }

    private void closeKeyboard()
    {
        View view=getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
