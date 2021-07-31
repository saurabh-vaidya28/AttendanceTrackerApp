package com.example.attendancetracker.admin.StudentAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.attendancetracker.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class pgdsp extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    asdisplay ad;
    ListView lv;
    FirebaseDatabase database;
    String st,yr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgdsp);
        Bundle bundle = getIntent().getExtras();
        st = bundle.getString("pg");
        yr = bundle.getString("year");

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        lv = findViewById(R.id.display);
        ad = new asdisplay();
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.studentdisplay, R.id.userinfo, arrayList);
        if (st.equals("MCom")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Post Graduate").child("MCom").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Post Graduate").child("MCom").child("SY");
                    break;
            }
        }

        if (st.equals("MSc-Chemistry")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Post Graduate").child("MSc-Chemistry").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Post Graduate").child("MSc-Chemistry").child("SY");
                    break;
            }
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ad = ds.getValue(asdisplay.class);
                    arrayList.add("Id" + ad.getUID() + "\n" + "Name" + ad.getName());
                }
                lv.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
