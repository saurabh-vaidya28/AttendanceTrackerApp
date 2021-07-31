package com.example.attendancetracker.ui.student_changepasswd;

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

public class studentChangePassword extends Fragment {

    private EditText passwd,passConf;
    private Button btn;
    private ProgressDialog dialog;
    private FirebaseAuth auth;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_changepassword, container, false);

        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        passwd=(EditText)getView().findViewById(R.id.password);
        passConf=(EditText)getView().findViewById(R.id.passwordConf);
        btn=(Button)getView().findViewById(R.id.change);
        dialog=new ProgressDialog(getActivity());

        auth=FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password= passwd.getText().toString().trim();
                String password1= passConf.getText().toString().trim();

                if(TextUtils.isEmpty(password))
                {
                    passwd.setError("please enter password");
                    return;
                }
                if(!password1.equals(password))
                {
                    passConf.setError("password do not match");
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
            dialog.setMessage("Changing Password!! Please Wait");
            dialog.show();

            user.updatePassword(passwd.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(getContext(),"Your password has been successfully changed",Toast.LENGTH_LONG).show();
                                auth.signOut();
                                getActivity().finish();
                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                            else {
                                dialog.dismiss();
                                Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }

                        }
                    });
        }
    }

}