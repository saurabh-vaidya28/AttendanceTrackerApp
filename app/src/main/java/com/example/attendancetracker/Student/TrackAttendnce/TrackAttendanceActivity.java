package com.example.attendancetracker.Student.TrackAttendnce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Student.Mark_Attendance_Database;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class TrackAttendanceActivity extends AppCompatActivity {

    private TextView _count;
    private String userId;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private MyAdapterAttendance myAdapterAttendance;
    private AttendanceAdapter adapter;

    private List<Mark_Attendance_Database> list;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        //Check if recyclerView is empty or not
        isEmptyRecycler();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_attendance);
        setTitle("Track Attendance");

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);
        Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);
        dialog.show();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {
            userId = "Test";
        }


        _count=(TextView)findViewById(R.id.count_attendance);
//        String _countAttendance=String.valueOf(adapter.mark_atten_data.size());

        int count=0;
        if(adapter != null)
        {
            count=adapter.getItemCount();

            _count.setText("Count: "+count);
        }
        else {
            _count.setText("Count: 0");
        }


        //RecyclerView
        recyclerView=(RecyclerView)findViewById(R.id.myAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Mark_Attendance_Database> options =
                new FirebaseRecyclerOptions.Builder<Mark_Attendance_Database>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Attendance_Combined/" + userId ), Mark_Attendance_Database.class)
                        .build();

        adapter = new AttendanceAdapter(options);
        recyclerView.setAdapter(adapter);

/*
        list=new ArrayList<>();

        reference=FirebaseDatabase.getInstance().getReference()
                .child("Attendance")
                .child("Under Graduate")
                .child("BMM")
                .child("Sem2")
                .child(mAuth.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Mark_Attendance_Database tp = dataSnapshot1.getValue(Mark_Attendance_Database.class);
                        list.add(tp);
                    }
                    myAdapterAttendance = new MyAdapterLecture(list);
                    recyclerView.setAdapter(myAdapterAttendance);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TrackAttendanceActivity.this, "Opss... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    private void isEmptyRecycler() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Attendance_Combined/" + userId );
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    findViewById(R.id.empty_view1).setVisibility(View.GONE);
                    dialog.dismiss();
                }
                else{
                    findViewById(R.id.empty_view1).setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    Toast.makeText(TrackAttendanceActivity.this,"Database is empty",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.search_track,menu);
//
//        MenuItem searchItem=menu.findItem(R.id.action_search);
//        SearchView searchView=(SearchView)searchItem.getActionView();
//
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                myAdapterAttendance.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }
}
