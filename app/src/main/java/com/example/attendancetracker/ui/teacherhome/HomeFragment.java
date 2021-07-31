package com.example.attendancetracker.ui.teacherhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.JuniorCollege1;
import com.example.attendancetracker.Teacher.PostGraduate1;
import com.example.attendancetracker.Teacher.UnderGraduate1;
import com.example.attendancetracker.Teacher.juniorCollegeLecture;
import com.example.attendancetracker.Teacher.postGraduateLecture;
import com.example.attendancetracker.Teacher.underGraduateLecture;

public class HomeFragment extends Fragment  implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    private CardView _jcCollege,_ugCollege,_pgCollege;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacherhome, container, false);


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

    @Override
    public void onClick(View v) {
        if(v==_jcCollege)
        {
            startActivity(new Intent(getContext(), JuniorCollege1.class));
        }

        if(v==_ugCollege)
        {
            startActivity(new Intent(getContext(), UnderGraduate1.class));
        }

        if(v==_pgCollege)
        {
            startActivity(new Intent(getContext(), PostGraduate1.class));
        }

    }

}