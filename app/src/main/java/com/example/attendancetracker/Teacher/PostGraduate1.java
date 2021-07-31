package com.example.attendancetracker.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostGraduate1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView _lec_pg_teacherID, _lec_pg_teacherName,_subjectDisplay;
    //    private String text1;
    private SearchableSpinner _department;
    private SearchableSpinner _semester;
    private SearchableSpinner _subCodeName;
    Button _generate;
    ImageView _qrCode;
    private FirebaseAuth firebaseAuth_lec_pg;
    DatabaseReference databaseReference,databaseReference1, databaseReference_lec_pg;
    Lecture lecture;
    private ProgressBar _progressBarPG;

    public String[] dept = new String[]{"Please select department",  "MCOM","MSc-Chemistry"};
    public String[] sem = new String[]{"Please select semester","Sem-1","Sem-2", "Sem-3", "Sem-4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_graduate1);
        setTitle("Lecture Instance");

        _lec_pg_teacherID = (TextView) findViewById(R.id.lec_teacherID_pg);
        _lec_pg_teacherName = (TextView) findViewById(R.id.lec_teacherName_pg);
//        _subjectDisplay=(TextView)findViewById(R.id.subject);

        _department = (SearchableSpinner) findViewById(R.id.department);
        _semester = (SearchableSpinner) findViewById(R.id.semPG);
        _subCodeName = (SearchableSpinner) findViewById(R.id.subCodeName);
//        _semester = (SearchableSpinner)findViewById(R.id.semester);

        final String a;
        a = (String) _department.getSelectedItem();
        ArrayAdapter<CharSequence> adaptera = ArrayAdapter.createFromResource(getApplicationContext(), R.array.PostGraduate, R.layout.support_simple_spinner_dropdown_item);
        _department.setAdapter(adaptera);

        final String b;
        b = (String) _semester.getSelectedItem();
        ArrayAdapter<CharSequence> adapterb = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SemesterPG, R.layout.support_simple_spinner_dropdown_item);
        _semester.setAdapter(adapterb);

        _progressBarPG = (ProgressBar) findViewById(R.id.progressBarPG);

        _generate = (Button) findViewById(R.id.generate);
        _qrCode = (ImageView) findViewById(R.id.qrCode);

        //for date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Date currentTime= android.icu.util.Calendar.getInstance().getTime();


        lecture = new Lecture();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lecture-Teacher").child("Post Graduate");
        databaseReference1= FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-Teacher").child("Post_Graduate");

        firebaseAuth_lec_pg = FirebaseAuth.getInstance();
        databaseReference_lec_pg = FirebaseDatabase.getInstance().getReference().child("Teacher_Details").child(firebaseAuth_lec_pg.getUid());

        databaseReference_lec_pg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lec_ug_Code = dataSnapshot.child("empCode_of_Teacher").getValue().toString();
                _lec_pg_teacherID.setText(lec_ug_Code);
                String lec_ug_Name = dataSnapshot.child("name_of_Teacher").getValue().toString();
                _lec_pg_teacherName.setText(lec_ug_Name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        _generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addDetails();
                closeKeyboard();
//                    Toast.makeText(getContext(),"Data inserted successfully",Toast.LENGTH_LONG).show();


            }
        });

        _department.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, dept);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _department.setAdapter(adapter);

        _semester.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sem);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _semester.setAdapter(adapter1);

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String a,b;
        a = (String) _department.getSelectedItem();
        b=(String)_semester.getSelectedItem();

        if(a.equals("Please select department") || b.equals("Please select semester"))
        {
            empty();
        }
        if (a.equals("Please select department") && b.equals("Please select semester")) {
            empty();
        }
        else if (a.equals("MCOM") && b.equals("Sem-1")) { mcomSem1(); }   else if (a.equals("MCOM") && b.equals("Sem-2")) {mcomSem2();}
        else if (a.equals("MCOM") && b.equals("Sem-3")){ mcomSem3(); }    else if (a.equals("MCOM") && b.equals("Sem-4")) {mcomSem4();}

        else if (a.equals("MSc-Chemistry") && b.equals("Sem-1")) { mscSem1(); }    else if (a.equals("MSc-Chemistry") && b.equals("Sem-2")) { mscSem2();}
        else if (a.equals("MSc-Chemistry") && b.equals("Sem-3")){ mscSem3(); }     else if (a.equals("MSc-Chemistry") && b.equals("Sem-4")) {mscSem4();}

        else {

        }
    }
    private void empty() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void mcomSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Business Environment");
        arrayList.add("Corporate Accounting");
        arrayList.add("Managerial Economics");
        arrayList.add("Organization Behavior");
        arrayList.add("Statistics for Decision Making");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void mcomSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Research Methodology for. Business");
        arrayList.add("Economics for Business. Decisions");
        arrayList.add("Macro Economics concepts and Applications");
        arrayList.add("Cost and Management. Accounting");
        arrayList.add("Business Ethics and Corporate. Social Responsibility");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void mcomSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Financial Management");
        arrayList.add("Acounting and Finance");
        arrayList.add("Research Mathodology");
        arrayList.add("Marketing Management-I");
        arrayList.add("Banking and Financial Institutions");
        arrayList.add("Human Resource Management-I");
        arrayList.add("International Business-I");
        arrayList.add("Advanced Financial Management");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void mcomSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Corporate Financial Accounting");
        arrayList.add("Indirect Tax-Introduction of Goods and Service Tax");
        arrayList.add("Financial Management");
        arrayList.add("International Financial Reporting Standards");
        arrayList.add("Personal Financial Planning");
        arrayList.add("Supply chain management and logistics");
        arrayList.add("Advertising and sales Management");
        arrayList.add("Retail Management");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void mscSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Inorganic Chemistry I");
        arrayList.add("Organic Chemistry I");
        arrayList.add("Physical Chemistry I");
        arrayList.add("Analytical Chemistry I");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void mscSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Inorganic Chemistry II");
        arrayList.add("Organic Chemistry II");
        arrayList.add("Physical Chemistry II");
        arrayList.add("Principles of Spectroscopy");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void mscSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Group Theory and Applications of Spectroscopy");
        arrayList.add("Organic Synthetic Methods");
        arrayList.add("Chemistry of Materials");
        arrayList.add("Nuclear & Radiochemistry");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void mscSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Analytical Chemistry – III");
        arrayList.add("Radioanalytical & Electroanalytical Methods");
        arrayList.add("Analytical Chemistry – IV Separation Methods");
        arrayList.add("Analytical Chemistry – V Hyphenated methods and automation");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void addDetails()
    {
        String teacherID = _lec_pg_teacherID.getText().toString();
        String name = _lec_pg_teacherName.getText().toString();

        String dept = (String) _department.getSelectedItem();
        String sem = (String) _semester.getSelectedItem();
        String s_c_n = (String) _subCodeName.getSelectedItem();

        final String text1 = teacherID + "\n" + name + "\n" + s_c_n;

        if (_department.getSelectedItem().toString().equals("Please select department")) {
            Toast.makeText(getApplicationContext(), "Please select department", Toast.LENGTH_LONG).show();
            return;
        }
        if (_semester.getSelectedItem().toString().equals("Please select semester")){
            Toast.makeText(getApplicationContext(), "Please select semester", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(s_c_n)) {
            Toast.makeText(getApplicationContext(), "Please select subject", Toast.LENGTH_LONG).show();
            return;
        }

        lecture.setTeacherID(_lec_pg_teacherID.getText().toString().trim());
        lecture.setTeachersName(_lec_pg_teacherName.getText().toString().trim());
        lecture.setDepartment(_department.getSelectedItem().toString().trim());
        lecture.setYear(_semester.getSelectedItem().toString().trim());
        lecture.setTime(new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date()));
        lecture.setDate(DateFormat.getDateInstance(DateFormat.FULL).format(android.icu.util.Calendar.getInstance().getTime()));
        lecture.setSubCodeName(_subCodeName.getSelectedItem().toString().trim());

        _progressBarPG.setVisibility(View.VISIBLE);
        if (text1 != null && !text1.isEmpty()) {
            try {
                databaseReference1.child(firebaseAuth_lec_pg.getUid()).push().setValue(lecture);
                _progressBarPG.setVisibility(View.GONE);

            } catch (Exception e) {
                _progressBarPG.setVisibility(View.GONE);
                e.printStackTrace();
            }

            try {
                databaseReference.child(_department.getSelectedItem().toString())
                        .child(_semester.getSelectedItem().toString()).child(firebaseAuth_lec_pg.getUid()).push().setValue(lecture)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    _progressBarPG.setVisibility(View.GONE);
                                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                    BitMatrix bitMatrix = null;
                                    try {
                                        bitMatrix = multiFormatWriter.encode(text1, BarcodeFormat.QR_CODE, 500, 500);
                                    } catch (WriterException e) {
                                        e.printStackTrace();
                                    }
                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                    _qrCode.setImageBitmap(bitmap);
                                    Toast.makeText(getApplicationContext(), "Lecture instance created successfully", Toast.LENGTH_LONG).show();


                                } else {
                                    _progressBarPG.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            } catch (Exception e) {
                _progressBarPG.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();


            }
        }
        else
        {
            _progressBarPG.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();
        }

    }
}
