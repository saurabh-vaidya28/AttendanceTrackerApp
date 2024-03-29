package com.example.attendancetracker.ui.teacherlecturedetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendancetracker.HOD.LectureHOD;
import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.Lecture;
import com.example.attendancetracker.ui.hodlecturedetails.HODLectureAdapter;
import com.example.attendancetracker.ui.hodlecturedetails.HODMyAdapterLecture;
import com.example.attendancetracker.ui.hodlecturedetails.hodJuniorLectureDetailsActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TeacherJuniorLectureDetailsActivity extends AppCompatActivity {

    private String userId;
    private ProgressDialog _jc_dialog;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerViewTeacher;
    private MyAdapterLecture myAdapterLecture;
    private LectureAdapter lectureAdapter;

    private List<Lecture> list;

    @Override
    public void onStart() {
        super.onStart();
        lectureAdapter.startListening();
        //Check if recyclerView is empty or not
        isEmptyRecycler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_junior_lecture_details);
        setTitle("Lecture Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _jc_dialog = new ProgressDialog(TeacherJuniorLectureDetailsActivity.this);
        _jc_dialog.setMessage("Loading...");
        _jc_dialog.setIndeterminate(true);
        Drawable drawable = new ProgressBar(TeacherJuniorLectureDetailsActivity.this).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(TeacherJuniorLectureDetailsActivity.this, R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);
        _jc_dialog.setIndeterminateDrawable(drawable);
        _jc_dialog.show();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {
            userId = "Test";
        }

        //RecyclerView
        recyclerViewTeacher=(RecyclerView)findViewById(R.id.junior_lectureTeacher);
        recyclerViewTeacher.setLayoutManager(new LinearLayoutManager(TeacherJuniorLectureDetailsActivity.this));


        FirebaseRecyclerOptions<Lecture> options =
                new FirebaseRecyclerOptions.Builder<Lecture>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-Teacher/Junior_College/" + userId ), Lecture.class)
                        .build();

        lectureAdapter = new LectureAdapter(options);
        recyclerViewTeacher.setAdapter(lectureAdapter);

    }
    private void isEmptyRecycler() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-Teacher/Junior_College/" + userId );
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    findViewById(R.id.junior_empty_viewTeacher).setVisibility(View.GONE);
                    _jc_dialog.dismiss();
                }
                else{
                    findViewById(R.id.junior_empty_viewTeacher).setVisibility(View.VISIBLE);
                    _jc_dialog.dismiss();
                    Toast.makeText(TeacherJuniorLectureDetailsActivity.this,"Database is empty",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        lectureAdapter.stopListening();
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