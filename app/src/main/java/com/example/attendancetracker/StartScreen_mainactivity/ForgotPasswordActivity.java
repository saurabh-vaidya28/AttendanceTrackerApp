package com.example.attendancetracker.StartScreen_mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.attendancetracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userEmail;
    private Button b_fp;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle("Forgot Password");
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#424D72"));
        actionBar.setBackgroundDrawable(colorDrawable);

        b_fp =(Button) findViewById(R.id.b_fp);
        userEmail=(EditText)findViewById(R.id.edit_fp);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        firebaseAuth=FirebaseAuth.getInstance();

        b_fp.setOnClickListener(this);


    }

    private void forgotPassword()
    {
        //getting email from edit texts
        String email = userEmail.getText().toString().trim();

        //checking if email is empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgotPasswordActivity.this,"password sent to your email",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(ForgotPasswordActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view==b_fp)
        {
            forgotPassword();

        }
    }
}
