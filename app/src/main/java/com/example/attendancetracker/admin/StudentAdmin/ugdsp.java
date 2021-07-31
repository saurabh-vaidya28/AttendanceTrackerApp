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
public class ugdsp extends AppCompatActivity {

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
        setContentView(R.layout.activity_ugdsp);
        Bundle bundle = getIntent().getExtras();
        st = bundle.getString("ug");
        yr = bundle.getString("year");

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        lv = findViewById(R.id.display);
        ad = new asdisplay();
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.studentdisplay, R.id.userinfo, arrayList);

        if (st.equals("BMM")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BMM").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BMM").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BMM").child("TY");
                    break;
            }
        }
        if (st.equals("BSc-IT")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BSc-IT").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BSc-IT").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BSc-IT").child("TY");
                    break;
            }
        }
        if (st.equals("BCOM")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BCOM").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BCOM").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BCOM").child("TY");
                    break;
            }

        }
        if (st.equals("BA")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BA").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BA").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BA").child("TY");
                    break;
            }
        }
        if (st.equals("BSc")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BSc").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BSc").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BSc").child("TY");
                    break;
            }
        }
        if (st.equals("BBI")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BBI").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BBI").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BBI").child("TY");
                    break;
            }
        }
        if (st.equals("BFM")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BFM").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BFM").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BFM").child("TY");
                    break;
            }
        }
        if (st.equals("BMS")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BMS").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BMS").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BMS").child("TY");
                    break;
            }
        }
        if (st.equals("BVoc-SD")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BVoc-SD").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BVoc-SD").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BVoc-SD").child("TY");
                    break;
            }
        }
        if (st.equals("BVoc-TT")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BVoc-TT").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BVoc-TT").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BVoc-TT").child("TY");
                    break;
            }
        }
        if (st.equals("BAF")) {
            switch (yr) {
                case "FY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BAF").child("FY");
                    break;
                case "SY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BAF").child("SY");
                    break;
                case "TY":
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Under Graduate").child("BAF").child("TY");
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
