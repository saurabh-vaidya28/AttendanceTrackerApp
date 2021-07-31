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

import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.Lecture;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TeacherPostLectureDetailsActivity extends AppCompatActivity {

    private String userId;
    private ProgressDialog _pg_dialog;
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
        setContentView(R.layout.activity_teacher_post_lecture_details_activty);
        setTitle("Lecture Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _pg_dialog = new ProgressDialog(TeacherPostLectureDetailsActivity.this);
        _pg_dialog.setMessage("Loading...");
        _pg_dialog.setIndeterminate(true);
        Drawable drawable = new ProgressBar(TeacherPostLectureDetailsActivity.this).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(TeacherPostLectureDetailsActivity.this, R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);
        _pg_dialog.setIndeterminateDrawable(drawable);
        _pg_dialog.show();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {
            userId = "Test";
        }

        //RecyclerView
        recyclerViewTeacher=(RecyclerView)findViewById(R.id.post_lectureTeacher);
        recyclerViewTeacher.setLayoutManager(new LinearLayoutManager(TeacherPostLectureDetailsActivity.this));


        FirebaseRecyclerOptions<Lecture> options =
                new FirebaseRecyclerOptions.Builder<Lecture>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-Teacher/Post_Graduate/" + userId ), Lecture.class)
                        .build();

        lectureAdapter = new LectureAdapter(options);
        recyclerViewTeacher.setAdapter(lectureAdapter);

    }
    private void isEmptyRecycler() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-Teacher/Post_Graduate/" + userId );
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    findViewById(R.id.post_empty_viewTeacher).setVisibility(View.GONE);
                    _pg_dialog.dismiss();
                }
                else{
                    findViewById(R.id.post_empty_viewTeacher).setVisibility(View.VISIBLE);
                    _pg_dialog.dismiss();
                    Toast.makeText(TeacherPostLectureDetailsActivity.this,"Database is empty",Toast.LENGTH_LONG).show();
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