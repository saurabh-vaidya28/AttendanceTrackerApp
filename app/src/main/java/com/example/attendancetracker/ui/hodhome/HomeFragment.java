package com.example.attendancetracker.ui.hodhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.attendancetracker.HOD.juniorCollegeLectureHOD;
import com.example.attendancetracker.HOD.postGraduateLectureHOD;
import com.example.attendancetracker.HOD.underGraduateLectureHOD;
import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.juniorCollegeLecture;
import com.example.attendancetracker.Teacher.postGraduateLecture;
import com.example.attendancetracker.Teacher.underGraduateLecture;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    private CardView _jcCollege,_ugCollege,_pgCollege;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hodhome, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        _jcCollege = (CardView) getView().findViewById(R.id.jcCollege);
        _ugCollege = (CardView) getView().findViewById(R.id.ugCollege);
        _pgCollege = (CardView) getView().findViewById(R.id.pgCollege);



        _jcCollege.setOnClickListener(this);
        _ugCollege.setOnClickListener(this);
        _pgCollege.setOnClickListener(this);

    }

    private void lecInstance()
    {

    }
    @Override
    public void onClick(View v) {

        if(v==_jcCollege)
        {
            startActivity(new Intent(getContext(), juniorCollegeLectureHOD.class));
        }

        if(v==_ugCollege)
        {
            startActivity(new Intent(getContext(), underGraduateLectureHOD.class));
        }

        if(v==_pgCollege)
        {
            startActivity(new Intent(getContext(), postGraduateLectureHOD.class));
        }

    }
}