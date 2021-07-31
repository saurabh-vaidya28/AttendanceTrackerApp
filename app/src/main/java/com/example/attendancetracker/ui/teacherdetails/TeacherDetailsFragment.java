package com.example.attendancetracker.ui.teacherdetails;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class TeacherDetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    private EditText _teacherName,_teacherEmpCode,_teacherPhone;
    private Button _teacherAdd;
    private ProgressBar _t_progress;
    private FirebaseAuth firebaseAuthT;
    private DatabaseReference reffTAll,reffT;
    private Teacher teacherDetails;

    public SearchableSpinner _college;
    private SearchableSpinner _department;
    private SearchableSpinner _year;
    private SearchableSpinner _semester;
    public String[] clg = new String[]{"Please select","Junior College", "Under Graduate", "Post Graduate"};
    public long maxid = 0;

    private DetailsViewModel detailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacherdetails, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        _teacherName=getView().findViewById(R.id.t_name);
        _teacherEmpCode=getView().findViewById(R.id.t_ID);
        _teacherPhone=getView().findViewById(R.id.t_phone);
        _t_progress=getView().findViewById(R.id.t_progress);
        _teacherAdd=getView().findViewById(R.id.t_save);

        _college =getView().findViewById(R.id.college);
        _department = getView().findViewById(R.id.department);
        _year = getView().findViewById(R.id.year);
        _semester = getView().findViewById(R.id.semester);

        final String a;
        a = (String) _college.getSelectedItem();
        ArrayAdapter<CharSequence> adaptera=ArrayAdapter.createFromResource(getContext(), R.array.College,R.layout.support_simple_spinner_dropdown_item);
        _college.setAdapter(adaptera);

        teacherDetails=new Teacher();
        firebaseAuthT =FirebaseAuth.getInstance();
        reffTAll=FirebaseDatabase.getInstance().getReference().child("Teacher_Details");
        reffT=FirebaseDatabase.getInstance().getReference().child("Organised_Teacher_Details");

        _college.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getView().getContext(),android.R.layout.simple_spinner_item, clg);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _college.setAdapter(adapter);

       _teacherAdd.setOnClickListener(this);
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

    private void addDetails()
    {
        String nameT=_teacherName.getText().toString().trim();
        String empCode=_teacherEmpCode.getText().toString().trim();
        String mobileT=_teacherPhone.getText().toString().trim();

        String clg = (String) _college.getSelectedItem();
        String dept = (String) _department.getSelectedItem();
        String yr = (String) _year.getSelectedItem();
        String sem = (String) _semester.getSelectedItem();

        if(TextUtils.isEmpty(nameT)){
            Toast.makeText(getContext(),"Please enter your name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(empCode)){
            Toast.makeText(getContext(),"Please enter valid uid",Toast.LENGTH_LONG).show();
            return;
        }


        //checking for mobile no.
        if(TextUtils.isEmpty(mobileT)){
            Toast.makeText(getContext(),"Please enter mobile no.",Toast.LENGTH_LONG).show();
            return;
        }
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

        teacherDetails.setName_of_Teacher(_teacherName.getText().toString().trim());
        teacherDetails.setEmpCode_of_Teacher(_teacherEmpCode.getText().toString().trim());
        teacherDetails.setPhone_of_Teacher(_teacherPhone.getText().toString().trim());
        teacherDetails.setCollege(_college.getSelectedItem().toString().trim());
        teacherDetails.setDept(_department.getSelectedItem().toString().trim());
        teacherDetails.setYear(_year.getSelectedItem().toString().trim());
        teacherDetails.setSemester(_semester.getSelectedItem().toString().trim());

        _t_progress.setVisibility(View.VISIBLE);
        if((!TextUtils.isEmpty(nameT)) && (!TextUtils.isEmpty(empCode)) && (!TextUtils.isEmpty(mobileT)))
        {
            reffT.child(_college.getSelectedItem().toString()).child(_department.getSelectedItem().toString())
                    .child(_year.getSelectedItem().toString()).child(firebaseAuthT.getUid()).setValue(teacherDetails);
            reffTAll.child(firebaseAuthT.getUid()).setValue(teacherDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        _t_progress.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Details added successfully",Toast.LENGTH_LONG).show();
                        if (getActivity() instanceof BaseDrawerActivity){
                            ((BaseDrawerActivity)getActivity()).setDrawerEnabled(true);
                            ((BaseDrawerActivity)getActivity()).displaySelectedScreen(R.id.nav_teacherhome);
                        }
                    } else {
                        Toast.makeText(getActivity(),"Error Occured",Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }
    @Override
    public void onClick(View view) {
                if(view==_teacherAdd)
        {
            addDetails();
            closeKeyboard();
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

}
