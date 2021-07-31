package com.example.attendancetracker.ui.teacherprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.JuniorCollege1;
import com.example.attendancetracker.ui.teacherprofile.tdisplay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherProfileFragment extends Fragment implements View.OnClickListener {

    private TextView _set_empCode,_set_name,_set_phone,_set_email;
    private Button _updateDet_Teacher;

    private FirebaseAuth pAuthT;
    private DatabaseReference databaseReference,database_email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacherprofile, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        _set_empCode=(TextView)getView().findViewById(R.id.set_empCode);
        _set_name=(TextView)getView().findViewById(R.id.set_name);
        _set_email=(TextView)getView().findViewById(R.id.set_email);
        _set_phone=(TextView)getView().findViewById(R.id.set_phone);

        _updateDet_Teacher=(Button)getView().findViewById(R.id.updateDet_Teacher);
        _updateDet_Teacher.setOnClickListener(this);

        pAuthT = FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Teacher_Details").child(pAuthT.getUid());
        database_email=FirebaseDatabase.getInstance().getReference().child("Users").child(pAuthT.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueCode=dataSnapshot.child("empCode_of_Teacher").getValue().toString();
                _set_empCode.setText("Employee Code: "+valueCode);
                String valueName=dataSnapshot.child("name_of_Teacher").getValue().toString();
                _set_name.setText("Name: "+valueName);
                String valuePhone=dataSnapshot.child("phone_of_Teacher").getValue().toString();
                _set_phone.setText("Phone No: "+valuePhone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database_email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueEmail=dataSnapshot.child("email").getValue().toString();
                _set_email.setText("E-mail: "+valueEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==_updateDet_Teacher)
        {
            startActivity(new Intent(getContext(), UpdateProfileActivity.class));
        }
    }
}