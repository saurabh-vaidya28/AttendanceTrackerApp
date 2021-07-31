package com.example.attendancetracker.HOD;

import android.os.Bundle;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.TeacherHomeActivity;
import com.example.attendancetracker.ui.base.BaseDrawerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class HODHomeActivity extends BaseDrawerActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView _nav_bar_email_hod, _nav_bar_name_hod, _nav_bar_type_hod;
    private ImageView _nav_bar_profileImage_teacher;
    private FirebaseAuth pAuthHOD;
    private StorageReference storageReference11;
    private DatabaseReference databaseReference31;
    private DatabaseReference databaseReference32;
    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodhome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);

//        _nav_bar_profileImage_teacher = (ImageView) navHeaderView.findViewById(R.id.nav_bar_profileImage_teacher);
        _nav_bar_email_hod = (TextView) navHeaderView.findViewById(R.id.nav_bar_email_hod);
        _nav_bar_name_hod = (TextView) navHeaderView.findViewById(R.id.nav_bar_name_hod);
        _nav_bar_type_hod = (TextView) navHeaderView.findViewById(R.id.nav_bar_type_hod);

        pAuthHOD = FirebaseAuth.getInstance();
//        storageReference11= FirebaseStorage.getInstance().getReference().child("");
        databaseReference31 = FirebaseDatabase.getInstance().getReference().child("Users").child(pAuthHOD.getUid());
        databaseReference32 = FirebaseDatabase.getInstance().getReference().child("HOD_Details").child(pAuthHOD.getUid());

        databaseReference31.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String hod11 = dataSnapshot.child("email").getValue().toString();
                _nav_bar_email_hod.setText(hod11);
                String hodType = dataSnapshot.child("type").getValue().toString();
                _nav_bar_type_hod.setText("Category: " + hodType);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference32.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("name_of_HOD").exists()) {
                    _nav_bar_name_hod.setText("Your name");
                    displaySelectedScreen(R.id.nav_hoddetails);
                    setDrawerEnabled(false);
                    return;
                }
                String teacher12 = dataSnapshot.child("name_of_HOD").getValue().toString();
                _nav_bar_name_hod.setText(teacher12);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_hodhome, R.id.nav_hod_lecture_details, R.id.nav_hoddetails, R.id.nav_hodprofile, R.id.nav_hodchangepasswd,
                R.id.nav_hodlogout)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        toggle = new ActionBarDrawerToggle(HODHomeActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        displaySelectedScreen(R.id.nav_view);


    }

    @NonNull
    @Override
    protected NavController getNavController() {
        return navController;
    }

    @NonNull
    @Override
    protected DrawerLayout getDrawerLayout() {
        return drawer;
    }

    @NonNull
    @Override
    protected ActionBarDrawerToggle getToggle() {
        return toggle;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hodhome, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
