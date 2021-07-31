package com.example.attendancetracker.Student;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.attendancetracker.R;
import com.example.attendancetracker.ui.base.BaseDrawerActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentHomeActivity extends BaseDrawerActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView _nav_bar_email_student, _nav_bar_name_student, _nav_bar_type_student;
    private ImageView _nav_bar_profileImage_student;
    private FirebaseAuth pAuthS;
    private DatabaseReference databaseReference21;
    private DatabaseReference databaseReference22;
    NavController navController;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView = navigationView.getHeaderView(0);

        _nav_bar_profileImage_student = (ImageView) navHeaderView.findViewById(R.id.nav_bar_profileImage_student);
        _nav_bar_email_student = (TextView) navHeaderView.findViewById(R.id.nav_bar_email_student);
        _nav_bar_name_student = (TextView) navHeaderView.findViewById(R.id.nav_bar_name_student);
        _nav_bar_type_student = (TextView) navHeaderView.findViewById(R.id.nav_bar_type_student);

        pAuthS = FirebaseAuth.getInstance();
        databaseReference21 = FirebaseDatabase.getInstance().getReference().child("Users").child(pAuthS.getUid());
        databaseReference22 = FirebaseDatabase.getInstance().getReference().child("Student_Details").child(pAuthS.getUid());

        databaseReference21.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String student11 = dataSnapshot.child("email").getValue().toString();
                _nav_bar_email_student.setText(student11);
                String studentType = dataSnapshot.child("type").getValue().toString();
                _nav_bar_type_student.setText("Category: " + studentType);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


         databaseReference22.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (!dataSnapshot.child("name").exists()) {
                     _nav_bar_name_student.setText("Unknown User");
                     displaySelectedScreen(R.id.nav_stadddetails);
                     setDrawerEnabled(false);
                     return;
                 }
             String student12=dataSnapshot.child("name").getValue().toString();
             _nav_bar_name_student.setText(student12);

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_sthome, R.id.nav_stadddetails, R.id.nav_stprofile,R.id.nav_stchpasswd, R.id.nav_stlogout)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        toggle = new ActionBarDrawerToggle(StudentHomeActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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

/*

    public boolean displaySelectedScreen(@NonNull int menuItem) {
        //creating fragment object
        Fragment fragment = null;
        int id = menuItem;
        //initializing the fragment object which is selected
        switch (id) {
            case R.id.nav_sthome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new studentHome()).commit();
                break;
            case R.id.nav_stprofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new studentProfile()).commit();
                break;
            case R.id.nav_stadddetails:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudentDetailsFragment()).commit();
                break;
            case R.id.nav_stinfo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new studentInfo()).commit();
                break;
            case R.id.nav_stsub:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new studentSubject()).commit();
                break;
            case R.id.nav_stchpasswd:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new studentChangePassword()).commit();
                break;
            case R.id.nav_stlogout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new studentLogout()).commit();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.student, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*/


}
