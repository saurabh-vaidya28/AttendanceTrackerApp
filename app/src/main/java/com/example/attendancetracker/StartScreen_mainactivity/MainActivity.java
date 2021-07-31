package com.example.attendancetracker.StartScreen_mainactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancetracker.HOD.HODHomeActivity;
import com.example.attendancetracker.ParentHome.parent_home;
import com.example.attendancetracker.R;
import com.example.attendancetracker.Student.StudentHomeActivity;
import com.example.attendancetracker.Teacher.TeacherHomeActivity;
import com.example.attendancetracker.admin.AdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.attendancetracker.ui.base.BaseDrawerActivity.hideKeyboard;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog,progressDialog1;
    private Button b_login;
    private TextView signUp, fPass;
    private EditText textEmail, textPass;
    private ProgressBar _progressBarLogin;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private DataSnapshot dataSnapshot;

    String userType1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login Form");

        progressDialog=new ProgressDialog(this);
        progressDialog1=new ProgressDialog(this);
        b_login = findViewById(R.id.b_login);
        signUp = findViewById(R.id.signup);
        fPass = findViewById(R.id.forPass);
        textEmail = findViewById(R.id.textEmail);
        textPass = findViewById(R.id.textPassword);
        _progressBarLogin = findViewById(R.id.progressBarLogin);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
//              FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseAuth.getCurrentUser() != null) {

                    if (!checkIfEmailVerified()) {
                        showMessage("Please verify your account");
                        FirebaseAuth.getInstance().signOut();
                        return;
                    }


                    FirebaseUser currentUser1 = FirebaseAuth.getInstance().getCurrentUser();
                    String RegisteredUserID1 = currentUser1.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID1);
                    progressDialog1.setMessage("Already Logged In...");
                    progressDialog1.show();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String userTypea = (String) dataSnapshot.child("type").getValue();
                            if (userTypea == null) {
                                progressDialog1.dismiss();
                                showMessage("Unable to login, Please contact admin");
                                return;
                            }
                            if (userTypea.equals("HOD")) {
                                //start hod activity
                                progressDialog1.dismiss();
                                finish();
                                startActivity(new Intent(MainActivity.this, HODHomeActivity.class));

                            } else if (userTypea.equals("Teacher")) {
                                //start teachers activity
                                progressDialog1.dismiss();
                                finish();
                                startActivity(new Intent(MainActivity.this, TeacherHomeActivity.class));
                            } else if (userTypea.equals("Student")) {
                                //start student activity
                                progressDialog1.dismiss();
                                finish();
                                startActivity(new Intent(MainActivity.this, StudentHomeActivity.class));
                            } else if (userTypea.equals("Parent")) {
                                //start parent activity
                                progressDialog1.dismiss();
                                finish();
                                startActivity(new Intent(MainActivity.this, parent_home.class));
                            } else {
                                progressDialog1.dismiss();
                                Log.d("TAG", "Current User is null");
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    progressDialog1.dismiss();
                    Log.d("TAG", "Current User is null");
                }


            }
        };

        hideKeyboard(MainActivity.this);
        signUp.setOnClickListener(this);
        b_login.setOnClickListener(this);
        fPass.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void userLogin() {
        String email = textEmail.getText().toString().trim();
        String password = textPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (textEmail.getText().toString().trim().equals("admin") && textPass.getText().toString().trim().equals("admin")) {
            //start admin activity
            finish();
            startActivity(new Intent(MainActivity.this, AdminActivity.class));

        }
//        _progressBarLogin.setVisibility(View.VISIBLE);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

//                            _progressBarLogin.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (currentUser == null) return;
                            String RegisteredUserID = currentUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.child("type").exists()) {
                                        showMessage("User does not exist");
                                        return;
                                    }
                                    String userType = dataSnapshot.child("type").getValue().toString();

                                    if (userType.equals("HOD")) {
                                        Intent intentResident = new Intent(MainActivity.this, HODHomeActivity.class);
                                        intentResident.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intentResident);
                                        finish();
//                                        finish();
//                                        Intent intentResident = new Intent(MainActivity.this,hod_home.class);
//                                        intentResident.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intentResident);

                                    } else if (userType.equals("Teacher")) {
                                        Intent intentMain = new Intent(MainActivity.this, TeacherHomeActivity.class);
                                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intentMain);
                                        finish();
//                                        finish();
//                                        Intent intentResident = new Intent(MainActivity.this,TeacherHomeActivity.class);
//                                        intentResident.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intentResident);
                                    } else if (userType.equals("Student")) {
                                        Intent intentMain = new Intent(MainActivity.this, StudentHomeActivity.class);
                                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intentMain);
                                        finish();
//                                        finish();
//                                        Intent intentResident = new Intent(MainActivity.this,StudentHomeActivity.class);
//                                        intentResident.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intentResident);
                                    } else if (userType.equals("Parent")) {
                                        Intent intentMain = new Intent(MainActivity.this, parent_home.class);
                                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intentMain);
                                        finish();
//                                        finish();
//                                        Intent intentResident = new Intent(MainActivity.this,admin.class);
//                                        intentResident.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intentResident);
                                    } else {
                                        if ((textEmail.getText().toString() == "admin") && (textPass.getText().toString() == "admin")) {
                                            //start admin activity
//                                            finish();
                                            Intent intentResident = new Intent(MainActivity.this, AdminActivity.class);
                                            intentResident.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intentResident);
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
//                            _progressBarLogin.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Invalid email-id or password", Toast.LENGTH_SHORT).show();
                            return;
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
        if (view == b_login) {
            userLogin();
            closeKeyboard();

        }
        if (view == signUp) {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }

        if (view == fPass) {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
    }


    private boolean checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user.isEmailVerified();

    }


}
