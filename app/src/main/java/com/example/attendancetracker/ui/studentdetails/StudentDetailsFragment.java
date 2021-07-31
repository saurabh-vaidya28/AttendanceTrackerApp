package com.example.attendancetracker.ui.studentdetails;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.attendancetracker.R;
import com.example.attendancetracker.ui.base.BaseDrawerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class StudentDetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private EditText _studentName,_studentUID,_studentPhone,_parentEmail,_parentPhone;
    private Button _studentAdd;
    private ProgressBar _s_progress;
    private FirebaseAuth firebaseAuthS;
    private DatabaseReference reffSAll,reffS;
    private Student studentDetails;

    public SearchableSpinner _college;
    private SearchableSpinner _department;
    private SearchableSpinner _year;
    private SearchableSpinner _semester;
    public String[] clg = new String[]{"Please select","Junior College", "Under Graduate", "Post Graduate"};
    public long maxid = 0;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private DetailsViewModel detailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_studentdetails, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        _studentName=getView().findViewById(R.id.s_name);
        _studentUID=getView().findViewById(R.id.s_UID);
        _studentPhone=getView().findViewById(R.id.s_phone);
        _parentEmail=getView().findViewById(R.id.p_email);
        _parentPhone=getView().findViewById(R.id.p_phone);

        _college =getView().findViewById(R.id.college);
        _department = getView().findViewById(R.id.department);
        _year = getView().findViewById(R.id.year);
        _semester = getView().findViewById(R.id.semester);

        final String a;
        a = (String) _college.getSelectedItem();
        ArrayAdapter<CharSequence> adaptera=ArrayAdapter.createFromResource(getContext(), R.array.College,R.layout.support_simple_spinner_dropdown_item);
        _college.setAdapter(adaptera);

        _s_progress=getView().findViewById(R.id.s_progress);
        _studentAdd=getView().findViewById(R.id.s_save);

        studentDetails=new Student();
        firebaseAuthS =FirebaseAuth.getInstance();
        reffSAll=FirebaseDatabase.getInstance().getReference().child("Student_Details");
        reffS=FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details");


        _studentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addDetails();
                    closeKeyboard();
           }
        });

        _college.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getView().getContext(),android.R.layout.simple_spinner_item, clg);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _college.setAdapter(adapter);

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String a;
        a = (String) _college.getSelectedItem();
        if(a.equals("Please select")){
            empty();
        }
        else if (a.equals("Junior College")) {
            jadd();
        }
        else if (a.equals("Under Graduate")) {
            ugadd();

        } else {
            pgadd();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void empty(){
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList.add("");
        arrayList2.add("");
        arrayList3.add("");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList);
        _department.setAdapter(arrayAdapter);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList2);
        _year.setAdapter(arrayAdapter2);
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList3);
        _semester.setAdapter(arrayAdapter3);
    }
    private void jadd() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList.add("Science");
        arrayList.add("Commerce");
        arrayList.add("Arts");
        arrayList2.add("FYJC");
        arrayList2.add("SYJC");
        arrayList3.add("A");
        arrayList3.add("B");
        arrayList3.add("C");
        arrayList3.add("D");
        arrayList3.add("E");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList);
        _department.setAdapter(arrayAdapter);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList2);
        _year.setAdapter(arrayAdapter2);
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList3);
        _semester.setAdapter(arrayAdapter3);

    }

    private void ugadd() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList.add("BSc-IT");
        arrayList.add("BVoc-SD");
        arrayList.add("BVoc-TT");
        arrayList.add("BA");
        arrayList.add("BSc");
        arrayList.add("BCOM");
        arrayList.add("BMM");
        arrayList.add("BAF");
        arrayList.add("BBI");
        arrayList.add("BMS");
        arrayList2.add("FY");
        arrayList2.add("SY");
        arrayList2.add("TY");
        arrayList3.add("Sem1");
        arrayList3.add("Sem2");
        arrayList3.add("Sem3");
        arrayList3.add("Sem4");
        arrayList3.add("Sem5");
        arrayList3.add("Sem6");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList);
        _department.setAdapter(arrayAdapter);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList2);
        _year.setAdapter(arrayAdapter2);
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList3);
        _semester.setAdapter(arrayAdapter3);
    }

    private void pgadd() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        arrayList.add("MCom");
        arrayList.add("MSc-Chemistry");
        arrayList2.add("FY");
        arrayList2.add("SY");
        arrayList3.add("Sem1");
        arrayList3.add("Sem2");
        arrayList3.add("Sem3");
        arrayList3.add("Sem4");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList);
        _department.setAdapter(arrayAdapter);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList2);
        _year.setAdapter(arrayAdapter2);
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(getView().getContext(), android.R.layout.simple_spinner_item, arrayList3);
        _semester.setAdapter(arrayAdapter3);
    }

    public boolean isValidPhone(CharSequence phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if(phone.length() > 10)
            {
                check = false;

            }
            else
            {
                check = true;

            }
        }
        else
        {
            check=false;
        }
        return check;
    }

    private void addDetails()
    {
        String nameS=_studentName.getText().toString().trim();
        String uidS=_studentUID.getText().toString().trim();
        String mobileS=_studentPhone.getText().toString().trim();
        String emailP=_parentEmail.getText().toString().trim();


        String phoneP=_parentPhone.getText().toString().trim();

        String clg = (String) _college.getSelectedItem();
        String dept = (String) _department.getSelectedItem();
        String yr = (String) _year.getSelectedItem();
        String sem = (String) _semester.getSelectedItem();

        if(TextUtils.isEmpty(nameS)){
            _studentName.setError("enter your name");
//                Toast.makeText(getContext(),"Please enter your name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(uidS)){
            _studentUID.setError("enter your uid");
//            Toast.makeText(getContext(),"Please enter uid",Toast.LENGTH_LONG).show();
            return;
        }

        //checking for mobile no.
        if(TextUtils.isEmpty(mobileS)){
            _studentPhone.setError("enter mobile number");
//            Toast.makeText(getContext(),"Please enter mobile no.",Toast.LENGTH_LONG).show();
            return;
        }

//        if(isValidPhone(_studentPhone.getText().toString())){
//            Toast.makeText(getContext(),"Phone number is valid",Toast.LENGTH_SHORT).show();
//        }else {
//            _studentPhone.setError("number is invalid");
////            Toast.makeText(getContext(),"Phone number is not valid",Toast.LENGTH_SHORT).show();
//
//        }

//        if(TextUtils.isEmpty(emailP)){
//            Toast.makeText(getContext(),"Please enter email of your parents.",Toast.LENGTH_LONG).show();
//            return;
//        }
//        if(_parentEmail.getText().toString().isEmpty()) {
//                _parentEmail.setError("enter email address");
////            Toast.makeText(getContext(),"enter email address",Toast.LENGTH_SHORT).show();
//        }else {
//            if (_parentEmail.getText().toString().trim().matches(emailPattern)) {
//                Toast.makeText(getContext(),"valid email address",Toast.LENGTH_SHORT).show();
//            } else {
//                _parentEmail.setError("invalid email address");
////                Toast.makeText(getContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
//            }
//        }


        if(TextUtils.isEmpty(phoneP)){
            _parentPhone.setError("enter mobile number");
//            Toast.makeText(getContext(),"Please enter mobile no. of your parents.",Toast.LENGTH_LONG).show();
            return;
        }

//        if(isValidPhone(_parentPhone.getText().toString())){
//
//            Toast.makeText(getContext(),"Phone number is valid",Toast.LENGTH_SHORT).show();
//        }else {
//
//            Toast.makeText(getContext(),"Phone number is not valid",Toast.LENGTH_SHORT).show();
//
//        }

        if (TextUtils.isEmpty(clg)) {
            Toast.makeText(getView().getContext(), "Please select college", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(dept)) {
            Toast.makeText(getView().getContext(), "Please select department", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(yr)) {
            Toast.makeText(getView().getContext(), "Please select year", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sem)) {
            Toast.makeText(getView().getContext(), "Please select semester", Toast.LENGTH_LONG).show();
            return;
        }

        studentDetails.setName(_studentName.getText().toString().trim());
        studentDetails.setUID(_studentUID.getText().toString().trim());
        studentDetails.setStudent_Phone(_studentPhone.getText().toString().trim());
        studentDetails.setParent_Email(_parentEmail.getText().toString().trim());
        studentDetails.setParent_Phone(_parentPhone.getText().toString().trim());
        studentDetails.setCollege(_college.getSelectedItem().toString().trim());
        studentDetails.setDept(_department.getSelectedItem().toString().trim());
        studentDetails.setYear(_year.getSelectedItem().toString().trim());
        studentDetails.setSemester(_semester.getSelectedItem().toString().trim());

        _s_progress.setVisibility(View.VISIBLE);
        if((!TextUtils.isEmpty(nameS)) && (!TextUtils.isEmpty(uidS)) && (!TextUtils.isEmpty(mobileS))&& (!TextUtils.isEmpty(phoneP))&& (!TextUtils.isEmpty(emailP)))
        {
            reffS.child(_college.getSelectedItem().toString()).child(_department.getSelectedItem().toString())
                    .child(_year.getSelectedItem().toString()).child(firebaseAuthS.getUid()).setValue(studentDetails);
            reffSAll.child(firebaseAuthS.getUid()).setValue(studentDetails)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        _s_progress.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Details added successfully",Toast.LENGTH_LONG).show();
                        if (getActivity() instanceof BaseDrawerActivity){
                            ((BaseDrawerActivity)getActivity()).setDrawerEnabled(true);
                            ((BaseDrawerActivity)getActivity()).displaySelectedScreen(R.id.nav_sthome);
                        }
                    } else {
                        Toast.makeText(getActivity(),"Error Occured",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        else
        {
            _s_progress.setVisibility(View.GONE);
            Toast.makeText(getContext(),"Details has not been added",Toast.LENGTH_LONG).show();
        }

    }

}
