package com.example.attendancetracker.ui.hodprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.attendancetracker.R;
import com.example.attendancetracker.ui.teacherprofile.UpdateProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class HODProfileFragment extends Fragment implements View.OnClickListener {

    private TextView _set_empCode_hod,_set_name_hod,_set_phone_hod,_set_email_hod;
    private Button _updateDet_HOD;

    private FirebaseAuth pAuthH;
    private DatabaseReference databaseReference_hod,database_email_hod;

    private SendViewModel sendViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hodprofile, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        _set_empCode_hod=(TextView)getView().findViewById(R.id.set_empCode_hod);
        _set_name_hod=(TextView)getView().findViewById(R.id.set_name_hod);
        _set_email_hod=(TextView)getView().findViewById(R.id.set_email_hod);
        _set_phone_hod=(TextView)getView().findViewById(R.id.set_phone_hod);
        _updateDet_HOD=(Button)getView().findViewById(R.id.updateDet_HOD);

        pAuthH = FirebaseAuth.getInstance();
        databaseReference_hod= FirebaseDatabase.getInstance().getReference().child("HOD_Details").child(pAuthH.getUid());
        database_email_hod=FirebaseDatabase.getInstance().getReference().child("Users").child(pAuthH.getUid());

        databaseReference_hod.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueCode_hod=dataSnapshot.child("empCode_of_HOD").getValue().toString();
                _set_empCode_hod.setText("Employee Code: "+valueCode_hod);
                String valueName_hod=dataSnapshot.child("name_of_HOD").getValue().toString();
                _set_name_hod.setText("Name: "+valueName_hod);
                String valuePhone_hod=dataSnapshot.child("phone_of_HOD").getValue().toString();
                _set_phone_hod.setText("Phone No: "+valuePhone_hod);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database_email_hod.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueEmail_hod=dataSnapshot.child("email").getValue().toString();
                _set_email_hod.setText("E-mail: "+valueEmail_hod);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        _updateDet_HOD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==_updateDet_HOD)
        {
            startActivity(new Intent(getContext(), UpdateProfileHODActivity.class));
        }
    }
}