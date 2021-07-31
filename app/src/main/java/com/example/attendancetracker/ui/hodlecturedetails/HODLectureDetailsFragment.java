package com.example.attendancetracker.ui.hodlecturedetails;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetracker.HOD.LectureHOD;
import com.example.attendancetracker.HOD.juniorCollegeLectureHOD;
import com.example.attendancetracker.HOD.postGraduateLectureHOD;
import com.example.attendancetracker.HOD.underGraduateLectureHOD;
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

public class HODLectureDetailsFragment extends Fragment implements View.OnClickListener {

//    private TextView _no_reminder_text;
//    private String userId;
//    private ProgressDialog dialog;
//    private FirebaseAuth mAuth;
//    private RecyclerView recyclerViewHOD;
//    private HODMyAdapterLecture hodMyAdapterLecture;
//    private HODLectureAdapter adapterHOD;
//
//    private List<LectureHOD> list;

    private CardView _jcCollege_lec_details,_ugCollege_lec_details,_pgCollege_lec_details;
    private HODLectureDetailsViewModel mViewModel;

    public static HODLectureDetailsFragment newInstance() {
        return new HODLectureDetailsFragment();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapterHOD.startListening();
//        //Check if recyclerView is empty or not
//        isEmptyRecycler();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_h_o_d_lecture_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
//        _no_reminder_text=(TextView)getView().findViewById(R.id.no_reminder_text);
//        dialog = new ProgressDialog(getContext());
//        dialog.setMessage("Loading...");
//        dialog.setIndeterminate(true);
//        Drawable drawable = new ProgressBar(getContext()).getIndeterminateDrawable().mutate();
//        drawable.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent),
//                PorterDuff.Mode.SRC_IN);
//        dialog.setIndeterminateDrawable(drawable);
//        dialog.show();
//
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            userId = user.getUid();
//        } else {
//            userId = "Test";
//        }
//
//        //RecyclerView
//        recyclerViewHOD=(RecyclerView)getView().findViewById(R.id.mylectureHOD);
//        recyclerViewHOD.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//        FirebaseRecyclerOptions<LectureHOD> options =
//                new FirebaseRecyclerOptions.Builder<LectureHOD>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-HOD/" + userId ), LectureHOD.class)
//                        .build();
//
//        adapterHOD = new HODLectureAdapter(options);
//        recyclerViewHOD.setAdapter(adapterHOD);

        _jcCollege_lec_details = (CardView) getView().findViewById(R.id.jcCollege_lec_details);
        _ugCollege_lec_details = (CardView) getView().findViewById(R.id.ugCollege_lec_details);
        _pgCollege_lec_details = (CardView) getView().findViewById(R.id.pgCollege_lec_details);



        _jcCollege_lec_details.setOnClickListener(this);
        _ugCollege_lec_details.setOnClickListener(this);
        _pgCollege_lec_details.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==_jcCollege_lec_details)
        {
            startActivity(new Intent(getContext(), hodJuniorLectureDetailsActivity.class));
        }

        if(v==_ugCollege_lec_details)
        {
            startActivity(new Intent(getContext(), hodUnderLectureDetailsActvity.class));
        }

        if(v==_pgCollege_lec_details)
        {
            startActivity(new Intent(getContext(), hodPostLectureDetailsActivty.class));
        }

    }

//    private void isEmptyRecycler() {
//        Query query = FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-HOD/" + userId );
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    getView().findViewById(R.id.empty_viewHOD).setVisibility(View.GONE);
//                    dialog.dismiss();
//                }
//                else{
//                    getView().findViewById(R.id.empty_viewHOD).setVisibility(View.VISIBLE);
//                    dialog.dismiss();
//                    _no_reminder_text.setText("No Attendance Done");
//                    Toast.makeText(getContext(),"Database is empty",Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapterHOD.stopListening();
//    }

}
