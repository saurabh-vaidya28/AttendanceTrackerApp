package com.example.attendancetracker.ui.hodchangepassword;

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

public class HODChangePasswdFragment extends Fragment {

    private EditText passwdH,passConfH;
    private Button btnH;
    private ProgressDialog dialogH;
    private FirebaseAuth authH;

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_hodchangepassword, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    passwdH=(EditText)getView().findViewById(R.id.passwordH);
        passConfH=(EditText)getView().findViewById(R.id.passwordConfH);
        btnH=(Button)getView().findViewById(R.id.changeH);
        dialogH=new ProgressDialog(getActivity());

        authH=FirebaseAuth.getInstance();

        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password= passwdH.getText().toString().trim();
                String password1= passConfH.getText().toString().trim();

                if(TextUtils.isEmpty(password))
                {
                    passwdH.setError("please enter password");
                    return;
                }
                if(!password1.equals(password))
                {
                    passConfH.setError("password do not match");
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
            dialogH.setMessage("Changing Password!! Please Wait");
            dialogH.show();

            user.updatePassword(passwdH.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialogH.dismiss();
                                Toast.makeText(getContext(),"Your password has been successfully changed",Toast.LENGTH_LONG).show();
                                authH.signOut();
                                getActivity().finish();
                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                            else {
                                dialogH.dismiss();
                                Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }

                        }
                    });
        }
    }

}