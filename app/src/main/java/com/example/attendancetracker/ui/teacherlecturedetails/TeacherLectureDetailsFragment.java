package com.example.attendancetracker.ui.teacherlecturedetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.attendancetracker.R;
import com.example.attendancetracker.ui.hodlecturedetails.HODLectureDetailsViewModel;

public class TeacherLectureDetailsFragment extends Fragment implements View.OnClickListener{
    private CardView _jcCollege_lec_details,_ugCollege_lec_details,_pgCollege_lec_details;
    private HODLectureDetailsViewModel mViewModel;

    public static TeacherLectureDetailsFragment newInstance() {
        return new TeacherLectureDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacherlecturedetails, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

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
            startActivity(new Intent(getContext(), TeacherJuniorLectureDetailsActivity.class));
        }

        if(v==_ugCollege_lec_details)
        {
            startActivity(new Intent(getContext(), TeacherUnderLectureDetailsActivity.class));
        }

        if(v==_pgCollege_lec_details)
        {
            startActivity(new Intent(getContext(), TeacherPostLectureDetailsActivity.class));
        }

    }

}