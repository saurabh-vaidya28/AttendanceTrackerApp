package com.example.attendancetracker.ui.hoddetails;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.attendancetracker.R;
import com.example.attendancetracker.ui.base.BaseDrawerActivity;
import com.example.attendancetracker.ui.teacherdetails.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HODDetailsFragment extends Fragment implements View.OnClickListener{

    private EditText _hodName,_hodEmpCode,_hodPhone;
    private Button _hodAdd;
    private ProgressBar _h_progress;
    private FirebaseAuth firebaseAuthH;
    private DatabaseReference reffH;
    private HOD hodDetails;


    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoddetails, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        _hodName=getView().findViewById(R.id.h_name);
        _hodEmpCode=getView().findViewById(R.id.h_ID);
        _hodPhone=getView().findViewById(R.id.h_phone);
        _h_progress=getView().findViewById(R.id.h_progress);
        _hodAdd=getView().findViewById(R.id.h_save);


        hodDetails=new HOD();
        reffH= FirebaseDatabase.getInstance().getReference().child("HOD_Details");
        firebaseAuthH =FirebaseAuth.getInstance();


        _hodAdd.setOnClickListener(this);
    }

    private void addDetailsHOD() {
        String nameH = _hodName.getText().toString().trim();
        String empCodeH = _hodEmpCode.getText().toString().trim();
        String mobileH = _hodPhone.getText().toString().trim();

        if (TextUtils.isEmpty(nameH)) {
            _hodName.setError("Please enter your name");
            return;
        }

        if (TextUtils.isEmpty(empCodeH)) {
            _hodEmpCode.setError("Please enter valid uid");
            return;
        }


        //checking for mobile no.
        if (TextUtils.isEmpty(mobileH)) {
            _hodPhone.setError("Please enter mobile no.");
            return;
        }

        hodDetails.setName_of_HOD(_hodName.getText().toString().trim());
        hodDetails.setEmpCode_of_HOD(_hodEmpCode.getText().toString().trim());
        hodDetails.setPhone_of_HOD(_hodPhone.getText().toString().trim());


        _h_progress.setVisibility(View.VISIBLE);
        if ((!TextUtils.isEmpty(nameH)) && (!TextUtils.isEmpty(empCodeH)) && (!TextUtils.isEmpty(mobileH))) {
            reffH.child(firebaseAuthH.getUid()).setValue(hodDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                _h_progress.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Details added successfully", Toast.LENGTH_LONG).show();
                                if (getActivity() instanceof BaseDrawerActivity) {
                                    ((BaseDrawerActivity) getActivity()).setDrawerEnabled(true);
                                    ((BaseDrawerActivity) getActivity()).displaySelectedScreen(R.id.nav_hodhome);
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
    }

    private void closeKeyboard()
    {
        View view=getActivity().getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
        }
    }
    @Override
    public void onClick(View v) {

        if(v==_hodAdd)
        {
            addDetailsHOD();
            closeKeyboard();
        }

    }
}