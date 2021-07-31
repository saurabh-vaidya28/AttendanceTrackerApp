package com.example.attendancetracker.ui.teacherlogout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.attendancetracker.StartScreen_mainactivity.MainActivity;
import com.example.attendancetracker.R;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherLogoutFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FirebaseAuth.getInstance().signOut();
        getActivity().finish();
        startActivity(new Intent(getContext(), MainActivity.class));
        return inflater.inflate(R.layout.fragment_teacherlogout, container, false);

    }
}