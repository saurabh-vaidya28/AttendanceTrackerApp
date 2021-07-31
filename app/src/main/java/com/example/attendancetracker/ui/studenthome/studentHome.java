package com.example.attendancetracker.ui.studenthome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.attendancetracker.Student.MarkAttendanceActivity;
import com.example.attendancetracker.R;
import com.example.attendancetracker.Student.TrackAttendnce.TrackAttendanceActivity;

public class studentHome extends Fragment implements View.OnClickListener
{
    private CardView _mark_attendance,_track_attendance;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_studenthome, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        _mark_attendance = (CardView) getView().findViewById(R.id.mark_attendance);
        _track_attendance = (CardView) getView().findViewById(R.id.track_attendance);

        _mark_attendance.setOnClickListener(this);
        _track_attendance.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==_mark_attendance)
        {
            startActivity(new Intent(getContext(), MarkAttendanceActivity.class));
        }

        if(v==_track_attendance)
        {
            startActivity(new Intent(getContext(), TrackAttendanceActivity.class));
        }
    }
}