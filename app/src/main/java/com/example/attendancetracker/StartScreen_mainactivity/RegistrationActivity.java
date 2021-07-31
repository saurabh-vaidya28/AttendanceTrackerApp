package com.example.attendancetracker.StartScreen_mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.attendancetracker.ui.base.BaseDrawerActivity.hideKeyboard;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialogRegister;
    EditText _email,_password;
    Button _register;
    RadioGroup _type;
    RadioButton radioButtonUser;
    ProgressBar _progressBar_register;
    TextView _login;
    private FirebaseAuth firebaseAuth_register;
    DatabaseReference databaseReference;
    register_registration reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Registration Form");
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#630247"));
        actionBar.setBackgroundDrawable(colorDrawable);

        progressDialogRegister=new ProgressDialog(this);
        _email=findViewById(R.id.email);
        _password=findViewById(R.id.password);
        _type=findViewById(R.id.type);
        _progressBar_register=findViewById(R.id.progressBar_register);
        _register=findViewById(R.id.register);
        _login=findViewById(R.id.login);

        reg=new register_registration();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth_register= FirebaseAuth.getInstance();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        hideKeyboard(RegistrationActivity.this);
        _register.setOnClickListener(this);
        _login.setOnClickListener(this);

    }

    public void checkButton(View view){
        int radioIDYear=_type.getCheckedRadioButtonId();
        radioButtonUser=(RadioButton)findViewById(radioIDYear);

        Toast.makeText(this,"Selected User: "+radioButtonUser.getText(),Toast.LENGTH_LONG).show();
    }

    private void Register()
    {

        String email = _email.getText().toString().trim();
        String password  = _password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if (radioButtonUser==null){
            Toast.makeText(this,"Please select user type",Toast.LENGTH_LONG).show();
            return;
        }

        reg.setEmail(_email.getText().toString().trim());
        reg.setType(radioButtonUser.getText().toString().trim());

        progressDialogRegister.setMessage("Registering user...");
        progressDialogRegister.show();
//        _progressBar_register.setVisibility(View.VISIBLE);
        //creating a new user
        firebaseAuth_register.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            final AuthResult result = task.getResult();
                            Log.i("Saurabh", "onComplete: "+result.getUser());
                            if (result.getUser()!=null){
                                databaseReference.child(result.getUser().getUid()).setValue(reg)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        result.getUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            FirebaseAuth.getInstance().signOut();
//                                                            _progressBar_register.setVisibility(View.GONE);
                                                            progressDialogRegister.dismiss();
                                                            Toast.makeText(RegistrationActivity.this,"Registration Successfully. Please check your email for verification",Toast.LENGTH_LONG).show();
                                                            _email.setText("");
                                                            _password.setText("");
                                                            finish();
                                                        }
                                                        else
                                                        {
//                                                            _progressBar_register.setVisibility(View.GONE);
                                                            progressDialogRegister.dismiss();
                                                            Toast.makeText(RegistrationActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                });

                            } else {
//                                _progressBar_register.setVisibility(View.GONE);
                                progressDialogRegister.dismiss();
                                Toast.makeText(RegistrationActivity.this,"User empty",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
//                            _progressBar_register.setVisibility(View.GONE);
                            progressDialogRegister.dismiss();
                            Toast.makeText(RegistrationActivity.this,"Registration error",Toast.LENGTH_LONG).show();
                        }
                    }
                });

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
    public void onClick(View view) {
        if(view==_register)
        {
            Register();
            closeKeyboard();
        }
        if(view==_login)
        {
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
        }
    }
}

