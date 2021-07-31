package com.example.attendancetracker.ui.hodlecturedetails;

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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class hodJuniorLectureDetailsActivity extends AppCompatActivity {

    private String userId;
    private ProgressDialog _jc_dialog;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerViewHOD;
    private HODMyAdapterLecture hodMyAdapterLecture;
    private HODLectureAdapter adapterHOD;

    private List<LectureHOD> list;

    @Override
    public void onStart() {
        super.onStart();
        adapterHOD.startListening();
        //Check if recyclerView is empty or not
        isEmptyRecycler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_junior_lecture_details);
        setTitle("Lecture Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _jc_dialog = new ProgressDialog(hodJuniorLectureDetailsActivity.this);
        _jc_dialog.setMessage("Loading...");
        _jc_dialog.setIndeterminate(true);
        Drawable drawable = new ProgressBar(hodJuniorLectureDetailsActivity.this).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(hodJuniorLectureDetailsActivity.this, R.color.colorAccent),
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
        recyclerViewHOD=(RecyclerView)findViewById(R.id.junior_lectureHOD);
        recyclerViewHOD.setLayoutManager(new LinearLayoutManager(hodJuniorLectureDetailsActivity.this));


        FirebaseRecyclerOptions<LectureHOD> options =
                new FirebaseRecyclerOptions.Builder<LectureHOD>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-HOD/Junior_College/" + userId ), LectureHOD.class)
                        .build();

        adapterHOD = new HODLectureAdapter(options);
        recyclerViewHOD.setAdapter(adapterHOD);

    }
    private void isEmptyRecycler() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-HOD/Junior_College/" + userId );
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    findViewById(R.id.junior_empty_viewHOD).setVisibility(View.GONE);
                    _jc_dialog.dismiss();
                }
                else{
                    findViewById(R.id.junior_empty_viewHOD).setVisibility(View.VISIBLE);
                    _jc_dialog.dismiss();
                    Toast.makeText(hodJuniorLectureDetailsActivity.this,"Database is empty",Toast.LENGTH_LONG).show();
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
        adapterHOD.stopListening();
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
