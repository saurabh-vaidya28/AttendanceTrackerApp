package com.example.attendancetracker.ui.hodlogout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.attendancetracker.R;
import com.example.attendancetracker.StartScreen_mainactivity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HODLogoutFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FirebaseAuth.getInstance().signOut();
        getActivity().finish();
        startActivity(new Intent(getContext(), MainActivity.class));
        return inflater.inflate(R.layout.fragment_hodlogout, container, false);

    }
}