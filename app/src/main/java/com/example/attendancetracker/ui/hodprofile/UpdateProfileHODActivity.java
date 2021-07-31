package com.example.attendancetracker.ui.hodprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendancetracker.HOD.HODHomeActivity;
import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.TeacherHomeActivity;
import com.example.attendancetracker.ui.hoddetails.HOD;
import com.example.attendancetracker.ui.teacherprofile.UpdateProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfileHODActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText _hodNameU,_hodEmpCodeU,_hodPhoneU;
    private Button _hodAddU;
    private ProgressBar _h_progressU;
    private FirebaseAuth firebaseAuthHU;
    private DatabaseReference reffHU,reffHAllU;
    private HOD hodDetailsU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_h_o_d);
        setTitle("Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _hodNameU=findViewById(R.id.hod_nameU);
        _hodEmpCodeU=findViewById(R.id.hod_IDU);
        _hodPhoneU=findViewById(R.id.hod_phoneU);
        _h_progressU=findViewById(R.id.hod_progressU);
        _hodAddU=findViewById(R.id.hod_saveU);


        hodDetailsU=new HOD();
        reffHAllU= FirebaseDatabase.getInstance().getReference().child("HOD_Details");
        reffHU= FirebaseDatabase.getInstance().getReference().child("Organised_HOD_Details");
        firebaseAuthHU =FirebaseAuth.getInstance();


        _hodAddU.setOnClickListener(this);

    }
    private void addDetailsU()
    {
        String nameT=_hodNameU.getText().toString().trim();
        String empCode=_hodEmpCodeU.getText().toString().trim();
        String mobileT=_hodPhoneU.getText().toString().trim();

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

        hodDetailsU.setName_of_HOD(_hodNameU.getText().toString().trim());
        hodDetailsU.setEmpCode_of_HOD(_hodEmpCodeU.getText().toString().trim());
        hodDetailsU.setPhone_of_HOD(_hodPhoneU.getText().toString().trim());

        _h_progressU.setVisibility(View.VISIBLE);
        if((!TextUtils.isEmpty(nameT)) && (!TextUtils.isEmpty(empCode)) && (!TextUtils.isEmpty(mobileT)))
        {
            reffHU.child(firebaseAuthHU.getUid()).setValue(hodDetailsU);
            reffHAllU.child(firebaseAuthHU.getUid()).setValue(hodDetailsU)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                _h_progressU.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Details updated successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(UpdateProfileHODActivity.this, HODHomeActivity.class));
                            } else {
                                Toast.makeText(UpdateProfileHODActivity.this,"Error Occured, Please try again later",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }

    }

    @Override
    public void onClick(View v) {
        if(v==_hodAddU)
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
