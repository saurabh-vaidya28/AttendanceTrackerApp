package com.example.attendancetracker.Teacher;

import android.os.Bundle;

import com.example.attendancetracker.R;
import com.example.attendancetracker.ui.base.BaseDrawerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TeacherHomeActivity extends BaseDrawerActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView _nav_bar_email_teacher, _nav_bar_name_teacher, _nav_bar_type_teacher;
    private ImageView _nav_bar_profileImage_teacher;
    private FirebaseAuth pAuth;
    private StorageReference storageReference11;
    private DatabaseReference databaseReference11;
    private DatabaseReference databaseReference12;
    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);

        _nav_bar_profileImage_teacher = (ImageView) navHeaderView.findViewById(R.id.nav_bar_profileImage_teacher);
        _nav_bar_email_teacher = (TextView) navHeaderView.findViewById(R.id.nav_bar_email_teacher);
        _nav_bar_name_teacher = (TextView) navHeaderView.findViewById(R.id.nav_bar_name_teacher);
        _nav_bar_type_teacher = (TextView) navHeaderView.findViewById(R.id.nav_bar_type_teacher);

        pAuth = FirebaseAuth.getInstance();
//        storageReference11= FirebaseStorage.getInstance().getReference().child("");
        databaseReference11 = FirebaseDatabase.getInstance().getReference().child("Users").child(pAuth.getUid());
        databaseReference12 = FirebaseDatabase.getInstance().getReference().child("Teacher_Details").child(pAuth.getUid());

        databaseReference11.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String teacher11 = dataSnapshot.child("email").getValue().toString();
                _nav_bar_email_teacher.setText(teacher11);
                String teacherType = dataSnapshot.child("type").getValue().toString();
                _nav_bar_type_teacher.setText("Category: " + teacherType);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_teacherhome, R.id.nav_teacher_lecture_details, R.id.nav_teacherdetails, R.id.nav_teacherprofile, R.id.nav_teacherchangepasswd,
                R.id.nav_teacherlogout)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        toggle = new ActionBarDrawerToggle(TeacherHomeActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        displaySelectedScreen(R.id.nav_view);

        databaseReference12.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("name_of_Teacher").exists()) {
                    _nav_bar_name_teacher.setText("Your name");
                    displaySelectedScreen(R.id.nav_teacherdetails);
                    setDrawerEnabled(false);
                    return;
                }
                String teacher12 = dataSnapshot.child("name_of_Teacher").getValue().toString();
                _nav_bar_name_teacher.setText(teacher12);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected NavController getNavController() {
        return navController;
    }

    @Override
    protected DrawerLayout getDrawerLayout() {
        return drawer;
    }

    @Override
    protected ActionBarDrawerToggle getToggle() {
        return toggle;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
