package com.example.attendancetracker.ui.teacherchangepassword;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.attendancetracker.R;
import com.example.attendancetracker.StartScreen_mainactivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class TeacherChangePasswdFragment extends Fragment {

    private EditText passwdT,passConfT;
    private Button btnT;
    private ProgressDialog dialogT;
    private FirebaseAuth authT;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacherchangepassword, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        passwdT=(EditText)getView().findViewById(R.id.passwordT);
        passConfT=(EditText)getView().findViewById(R.id.passwordConfT);
        btnT=(Button)getView().findViewById(R.id.changeT);
        dialogT=new ProgressDialog(getActivity());

        authT=FirebaseAuth.getInstance();

        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password= passwdT.getText().toString().trim();
                String password1= passConfT.getText().toString().trim();

                if(TextUtils.isEmpty(password))
                {
                    passwdT.setError("please enter password");
                    return;
                }
                if(!password1.equals(password))
                {
                    passConfT.setError("password do not match");
                    return;
                }
                change(v);
                closeKeyboard();
            }
        });


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

    public  void change(View v){

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            dialogT.setMessage("Changing Password!! Please Wait");
            dialogT.show();

            user.updatePassword(passwdT.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialogT.dismiss();
                                Toast.makeText(getContext(),"Your password has been successfully changed",Toast.LENGTH_LONG).show();
                                authT.signOut();
                                getActivity().finish();
                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                            else {
                                dialogT.dismiss();
                                Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }

                        }
                    });
        }
    }

}