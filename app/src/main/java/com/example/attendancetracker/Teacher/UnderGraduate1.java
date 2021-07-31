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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class UnderGraduate1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextView _lec_ug_teacherID, _lec_ug_teacherName,_subjectDisplay;
    //    private String text1;
    private SearchableSpinner _department;
    private SearchableSpinner _semester;
    private SearchableSpinner _subCodeName;
    Button _generate;
    ImageView _qrCode;
    private FirebaseAuth firebaseAuth_lec_ug;
    DatabaseReference databaseReference,databaseReference1, databaseReference_lec_ug;
    Lecture lecture;
    private ProgressBar _progressBarUG;

    public String[] dept = new String[]{"Please select department",  "BCOM", "BA", "BSC", "BBI", "BAF", "BMS", "BMM", "BSc-IT", "BVoc-SD", "BVoc-TT"};
    public String[] sem = new String[]{"Please select semester","Sem-1","Sem-2", "Sem-3", "Sem-4", "Sem-5", "Sem-6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_graduate1);
        setTitle("Lecture Instance");

        _lec_ug_teacherID = (TextView) findViewById(R.id.lec_teacherID_ug);
        _lec_ug_teacherName = (TextView) findViewById(R.id.lec_teacherName_ug);
//        _subjectDisplay=(TextView)findViewById(R.id.subject);

        _department = (SearchableSpinner) findViewById(R.id.department);
        _semester = (SearchableSpinner) findViewById(R.id.semUG);
        _subCodeName = (SearchableSpinner) findViewById(R.id.subCodeName);
//        _semester = (SearchableSpinner)findViewById(R.id.semester);

        final String a;
        a = (String) _department.getSelectedItem();
        ArrayAdapter<CharSequence> adaptera = ArrayAdapter.createFromResource(getApplicationContext(), R.array.UnderGraduate, R.layout.support_simple_spinner_dropdown_item);
        _department.setAdapter(adaptera);

        final String b;
        b = (String) _semester.getSelectedItem();
        ArrayAdapter<CharSequence> adapterb = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SemesterUG, R.layout.support_simple_spinner_dropdown_item);
        _semester.setAdapter(adapterb);

        _progressBarUG = (ProgressBar) findViewById(R.id.progressBarUG);

        _generate = (Button) findViewById(R.id.generate);
        _qrCode = (ImageView) findViewById(R.id.qrCode);

        //for date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        lecture = new Lecture();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lecture-Teacher").child("Under Graduate");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-Teacher").child("Under_Graduate");

        firebaseAuth_lec_ug = FirebaseAuth.getInstance();
        databaseReference_lec_ug = FirebaseDatabase.getInstance().getReference().child("Teacher_Details").child(firebaseAuth_lec_ug.getUid());

        databaseReference_lec_ug.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lec_ug_Code = dataSnapshot.child("empCode_of_Teacher").getValue().toString();
                _lec_ug_teacherID.setText(lec_ug_Code);
                String lec_ug_Name = dataSnapshot.child("name_of_Teacher").getValue().toString();
                _lec_ug_teacherName.setText(lec_ug_Name);

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
        else if (a.equals("BCOM") && b.equals("Sem-1")) { bcomSem1(); }   else if (a.equals("BCOM") && b.equals("Sem-2")) { bcomSem2();}
        else if(a.equals("BCOM") && b.equals("Sem-3")){ bcomSem3(); }   else if (a.equals("BCOM") && b.equals("Sem-4")) {bcomSem4();}
        else if (a.equals("BCOM") && b.equals("Sem-5")) { bcomSem5(); } else if (a.equals("BCOM") && b.equals("Sem-6")){bcomSem6();}

        else if (a.equals("BA") && b.equals("Sem-1")) { baSem1(); }     else if (a.equals("BA") && b.equals("Sem-2")) { baSem2();}
        else if (a.equals("BA") && b.equals("Sem-3")){ baSem3(); }      else if (a.equals("BA") && b.equals("Sem-4")) {baSem4();}
        else if (a.equals("BA") && b.equals("Sem-5")) { baSem5(); }     else if (a.equals("BA") && b.equals("Sem-6")){baSem6();}

        else if (a.equals("BSC") && b.equals("Sem-1")) { bscSem1(); }    else if (a.equals("BSC") && b.equals("Sem-2")) { bscSem2();}
        else if (a.equals("BSC") && b.equals("Sem-3")){ bscSem3(); }     else if (a.equals("BSC") && b.equals("Sem-4")) {bscSem4();}
        else if (a.equals("BSC") && b.equals("Sem-5")) { bscSem5(); }    else if (a.equals("BSC") && b.equals("Sem-6")){bscSem6();}

        else if (a.equals("BBI") && b.equals("Sem-1")) { bbiSem1(); } else if (a.equals("BBI") && b.equals("Sem-2")) { bbiSem2();}
        else if (a.equals("BBI") && b.equals("Sem-3")){ bbiSem3(); }  else if (a.equals("BBI") && b.equals("Sem-4")) {bbiSem4();}
        else if (a.equals("BBI") && b.equals("Sem-5")) { bbiSem5();}  else if (a.equals("BBI") && b.equals("Sem-6")){bbiSem6();}

        else if (a.equals("BAF") && b.equals("Sem-1")) { bafSem1(); } else if (a.equals("BAF") && b.equals("Sem-2")) { bafSem2();}
        else if (a.equals("BAF") && b.equals("Sem-3")){ bafSem3(); }  else if (a.equals("BAF") && b.equals("Sem-4")) {bafSem4();}
        else if (a.equals("BAF") && b.equals("Sem-5")) { bafSem5();}  else if (a.equals("BAF") && b.equals("Sem-6")){bafSem6();}

        else if (a.equals("BMS") && b.equals("Sem-1")) { bmsSem1(); } else if (a.equals("BMS") && b.equals("Sem-2")) { bmsSem2();}
        else if (a.equals("BMS") && b.equals("Sem-3")){ bmsSem3(); }  else if (a.equals("BMS") && b.equals("Sem-4")) {bmsSem4();}
        else if (a.equals("BMS") && b.equals("Sem-5")) { bmsSem5();}  else if (a.equals("BMS") && b.equals("Sem-6")){bmsem6();}

        else if (a.equals("BMM") && b.equals("Sem-1")) { bmmSem1(); } else if (a.equals("BMM") && b.equals("Sem-2")) { bmmSem2();}
        else if (a.equals("BMM") && b.equals("Sem-3")){ bmmSem3(); }  else if (a.equals("BMM") && b.equals("Sem-4")) {bmmSem4();}
        else if (a.equals("BMM") && b.equals("Sem-5")) { bmmSem5();}  else if (a.equals("BMM") && b.equals("Sem-6")){bmmem6();}

        else if (a.equals("BSc-IT") && b.equals("Sem-1")) { bscitSem1(); } else if (a.equals("BSc-IT") && b.equals("Sem-2")) { bscitSem2();}
        else if (a.equals("BSc-IT") && b.equals("Sem-3")){ bscitSem3(); }  else if (a.equals("BSc-IT") && b.equals("Sem-4")) {bscitSem4();}
        else if (a.equals("BSc-IT") && b.equals("Sem-5")) { bscitSem5();}  else if (a.equals("BSc-IT") && b.equals("Sem-6")){bscitem6();}

        else if (a.equals("BVoc-SD") && b.equals("Sem-1")) { bvocsdSem1(); } else if (a.equals("BVoc-SD") && b.equals("Sem-2")) { bvocsdSem2();}
        else if (a.equals("BVoc-SD") && b.equals("Sem-3")){ bvocsdSem3(); }  else if (a.equals("BVoc-SD") && b.equals("Sem-4")) {bvocsdSem4();}
        else if (a.equals("BVoc-SD") && b.equals("Sem-5")) { bvocsdSem5();}  else if (a.equals("BVoc-SD") && b.equals("Sem-6")){bvocsdSem6();}

        else if (a.equals("BVoc-TT") && b.equals("Sem-1")) { bvocttSem1(); } else if (a.equals("BVoc-TT") && b.equals("Sem-2")) { bvocttSem2();}
        else if (a.equals("BVoc-TT") && b.equals("Sem-3")){ bvocttSem3(); }  else if (a.equals("BVoc-TT") && b.equals("Sem-4")) {bvocttSem4();}
        else if (a.equals("BVoc-TT") && b.equals("Sem-5")) { bvocttSem5();}  else if (a.equals("BVoc-TT") && b.equals("Sem-6")){bvocttSem6();}

        else{

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void empty() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
    }

    private void bcomSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CMAT101 /Mathematical and Statistical Techniques-I");
        arrayList.add("CECO101 / Business Economics-I");
        arrayList.add("CECO201 / Business Economics-II");
        arrayList.add("CEVS101 /Environmental Studies");
        arrayList.add("CFC101 /Foundation Course");
        arrayList.add("CACC101 /Financial Accountancy");
        arrayList.add("CCOM101 /Introduction to Business & Service Sector");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bcomSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CCOM201 /Introduction to Service Sector");
        arrayList.add("CECO201 / Business Economics");
        arrayList.add("CEVS201 /Environmental Studies II");
        arrayList.add("CACC201 /Financial Accountancy II");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bcomSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CMAT301 /Business Law I");
        arrayList.add("CCP301 /Computer Programming-I");
        arrayList.add("CEVS301 /Environmental Studies");
        arrayList.add("CFC301 /Citizens' Rights,Ecology,Science and Society");
        arrayList.add("CACC301 /Financial Accountancy III");
        arrayList.add("CACC302 /Management Accountancy");
        arrayList.add("CECO301 /Business Economics-III");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bcomSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CACC402 /Auditing");
        arrayList.add("CCOM401/ Finance");
        arrayList.add("CCOM402/ Advertising");
        arrayList.add("CCOM403 /Marketing Strategies");
        arrayList.add("CECO401 /Business Economics-IV");
        arrayList.add("AECO401 /Intermediate Macroeconomic Theory");
        arrayList.add("AECO402 /Indian Economy: Policy and Prospects");
        arrayList.add("CFC401 /Citizen's Rights,Ecology,Science and Society");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bcomSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CCOM501 /Marketing");
        arrayList.add("CCOM502 /Management and Organization Development");
        arrayList.add("CCOM503AC /Export Marketing");
        arrayList.add("CACC501 /Financial Accountancy-V");
        arrayList.add("CACC502 /Cost Accountancy-I");
        arrayList.add("CACC504 /Income Tax");
        arrayList.add("CACC505 /Business Management Accountancy-I");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bcomSem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CCOM601 /Human Resource Management");
        arrayList.add("CCOM602AC /Management by Organization Development");
        arrayList.add("CACC601 /Financial Accounting-II");
        arrayList.add("CACC602 / Cost Accountancy-II");
        arrayList.add("CCOM603AC /Export Marketing");
        arrayList.add("CACC604 /Goods and Service Tax");
        arrayList.add("CACC605 /Business Management Accountancy-II");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void baSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AECO101 /Basics of Microeconomics");
        arrayList.add("ACOM101 /Intoduction to Business Organisation");
        arrayList.add("AIHS101 /History of Early Modern India(1757-1857)");
        arrayList.add("APOL101 /The Constitutuin of India");
        arrayList.add("AECS101 /Effective Communication Skills in English");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void baSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AHIS201 /History of Modern India(1854-1947)");
        arrayList.add("ACOM201 /Introduction to Business Sector");
        arrayList.add("APSY201 /Fundamentals of Psychology");
        arrayList.add("SMAT201 /Calculus II");
        arrayList.add("SMAT202 /Alegbra II");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void baSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AECO301 /Intermediate Microeconomic Theory");
        arrayList.add("AECO302 /Indian Economy and Contemporary Issues-I");
        arrayList.add("APOL301 /Intorduction to Politics");
        arrayList.add("APOL302 /Public Administration");
        arrayList.add("AAC301 /Book Keeping and Accountancy");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void baSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AAC401 /Book Keeping and Accountancy");
        arrayList.add("AECO401 /Intermediate Macroeconomic Theory");
        arrayList.add("AECO402 /Indian Economy and Contemporary Issues-II");
        arrayList.add("APSY401 /Social Psychology II");
        arrayList.add("APSY402 /Development Pyschology");
        arrayList.add("APSY401AC /Psycholgy of Adjustment");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void baSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AECO501 /Advanced Microeconomic Theory");
        arrayList.add("AECO502 /Growth and Development");
        arrayList.add("AECO503 /Indian Financial System");
        arrayList.add("AECO504 /Elementary Mathematics for Economic Analysis");
        arrayList.add("AECO505 /Fundamentals of International Economics");
        arrayList.add("AECO506 /Elementary Statistics for Economic Analysis");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void baSem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AECO604 /Introduction to Econometrics-I");
        arrayList.add("APOL603 /India in World Politics");
        arrayList.add("AENG601 /Restoration and Neo Classical Period");
        arrayList.add("AENG602 /Literary Theory and Criticism II");
        arrayList.add("AENG603 /Popular Culture II");
        arrayList.add("AENG604 /The Victorian Age");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBOT101 /Algae,Fungi and Lichens");
        arrayList.add("SBOT102 /Genetics, Ecology and Industrial Botany");
        arrayList.add("SCHE101 /Concepts of Physical and Inorganic");
        arrayList.add("SCHE102 /Concepts of Organic and Inorganic");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBOT201 /Bryophyta, Pteridophta, Gymnosperms and Augiosperms");
        arrayList.add("SBOT202 /Anatomy,Physiology and Ethmobotany");
        arrayList.add("SCHE201 /Concepts of Physical and Inorganic Chemistry-II");
        arrayList.add("SCHE202 /Concepts of Organic and Co-ordianation Chemistry-II");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SMAT 301 /Calculus and Differential Equation III");
        arrayList.add("SMAT302 /Linear Algebra II");
        arrayList.add("SMAT303 /Data Analytics");
        arrayList.add("SMIC301 /Essentials of Molecular Biology");
        arrayList.add("SMIC302 /Research methodology, Biostatistics and Analytical techniques");
        arrayList.add("SMIC303 /Environmental and Applied Microbiology");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SLSC401 /Comparative Physiology");
        arrayList.add("SLSC402 /Life processes at the tissue, organ and organism levels");
        arrayList.add("SLSC403 /Population approach:population and communities as regulatory unit");
        arrayList.add("SPHY401 /Optics and Digital Electronics");
        arrayList.add("SPHY402 /Quantum Mechanics");
        arrayList.add("SPHY403 /Applied Physics-II");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SCHE501 /Advanced Physical Chemistry-I");
        arrayList.add("SCHE502 /Advanced Inorganic Chemistry-I");
        arrayList.add("SMAT501 /Integral Calculus");
        arrayList.add("SMAT502 /Algebra");
        arrayList.add("SMIC5AC /Food Production and Processing");
        arrayList.add("SPHY501 /Mathematical, Thermal and Statistical Physics");
        arrayList.add("SPHY502 /Electronics");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscSem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBOT601 /Bryophyta, Pteridophyta and Gymnosperms");
        arrayList.add("SBOT603 /Physiology, Genetics and Biostatistics");
        arrayList.add("SLSC601 /Genetics and Immunology II");
        arrayList.add("SLSC602 /Development Biology and Neurobiology II");
        arrayList.add("SLSC604 /Environmental Biotechnology II");
        arrayList.add("SPHY6AC /Digital Electronics, microprocessor and its applications, programming in C++");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bbiSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBBI101 /Effective Communication-I");
        arrayList.add("CBBI102 /Foundation Course-I");
        arrayList.add("CBBI103 /Accounting For Bankers");
        arrayList.add("CBBI104 /Quantitative Methods-I");
        arrayList.add("CBBI105 /Overview of Banking");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bbiSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBBI201 /Effective Communication-II");
        arrayList.add("CBBI202 /Organisational Behaviour");
        arrayList.add("CBBI203 /Business Organisation and Management");
        arrayList.add("CBBI204 /Practices of Banking");
        arrayList.add("CBBI205 /Introduction to Life Insurance");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bbiSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBBI301 /Business Law");
        arrayList.add("CBBI302 /Information Technology in Banking and Insurance");
        arrayList.add("CBBI303 /E-commerce");
        arrayList.add("CBBI303 /General Insurance");
        arrayList.add("CBBI305 /Corporate and Retail Banking");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bbiSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBBI401 /Business Research Methods-I");
        arrayList.add("CBBI402 /Information Technology in Banking and Insurance-II");
        arrayList.add("CBBI403 /Auditing and Ethics in Banking Insurance");
        arrayList.add("CBBI404 /Health Insurance");
        arrayList.add("CBBI405 /Rural Banking");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bbiSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBBI501 /Strategic Management");
        arrayList.add("CBBI502 /Research Methodology");
        arrayList.add("CBBI503 /International Banking and Finance");
        arrayList.add("CBBI504 /Financial Reporting and Analysis");
        arrayList.add("CBBI505 /Business Ethics and Corporate");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bbiSem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBBI601 /Security Analysis and Portfolio Management");
        arrayList.add("CBBI602 /Human Resource Management");
        arrayList.add("CBBI603 /Turnaround Management");
        arrayList.add("CBBI604 /Marketing in Banking and Insurance");
        arrayList.add("CBBI605 /Central Banking");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bafSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBAF101 /Effective Communications-1");
        arrayList.add("CBAF102 /Foundation Course-I");
        arrayList.add("CBAF103 /Commerce I-Business Environment and Enterprenuership");
        arrayList.add("CBAF104 /Quantitative Methods-I");
        arrayList.add("CBAF105 /Financial Accounting-I");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bafSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBAF201 /Effective Communications-II");
        arrayList.add("CBAF202 /Foundation Course-II");
        arrayList.add("CBAF203 /Business Organisation and Management");
        arrayList.add("CBAF204 /Quantitative Methods-II");
        arrayList.add("CBAF205 /Financial Accounting-II");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bafSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBAF301 /Business Law I");
        arrayList.add("CBAF302 /Information Technology in Accounting and Finance");
        arrayList.add("CBAF303 /E-Commerce");
        arrayList.add("CBAF304 /Financial Accounting-III");
        arrayList.add("CBAF305 /Indirect Taxation (GST)");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bafSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBAF401 /Business Research Methods-I");
        arrayList.add("CBAF402 /Information Technology in Accounting and Finance-II");
        arrayList.add("CBAF403 /Foundation Course IV");
        arrayList.add("CBAF404 /Management Accounting");
        arrayList.add("CBAF405 /Equity Investnebts-II");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bafSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBAF501 /Financial Accounting-V");
        arrayList.add("CBAF502 /Financial Accounting-VI");
        arrayList.add("CBAF503 /Financial Management-II");
        arrayList.add("CBAF504 /Cost Accounting-III");
        arrayList.add("CBAF505 /Taxation-IV");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bafSem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBAF601 /Financial Accounting-VII");
        arrayList.add("CBAF602 /Cost Accounting-IV");
        arrayList.add("CBAF603 /Financial Management-III");
        arrayList.add("CBAF604 /Taxation-V");
        arrayList.add("CBAF605 /Security Analysis and Portfolio Management");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmsSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBMS101 /Business Law");
        arrayList.add("CBMS102 /Foundation Course-I");
        arrayList.add("CBMS103 /Business Economics");
        arrayList.add("CBMS104 /Business Communication-I");
        arrayList.add("CBMS105 /Busimess Statistics");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmsSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBMS201 /Principles of Managemet");
        arrayList.add("CBMS202 /Priciples of Marketing");
        arrayList.add("CBMS203 /Industrial Law");
        arrayList.add("CBMS204 /Foundation Course-II");
        arrayList.add("CBMS205 /Business Communication-II");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmsSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBMS301 /Environment Management");
        arrayList.add("CBMS302 /Strategic Management");
        arrayList.add("CBMS303 /Business Planning and Enterpreneurship");
        arrayList.add("CBMS304 /Information Technology in Business Management");
        arrayList.add("CBMS305 /Cost accounting");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmsSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBMS401 /Information Technology in Business Management-II");
        arrayList.add("CBMS402 /Business Economics-II");
        arrayList.add("CBMS403 /Business Research Methods");
        arrayList.add("CBMS404 /Business Ethics and Governance");
        arrayList.add("CBMS405 /Auditing");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmsSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBMS501 /Logistics and SCM");
        arrayList.add("CBMS502 /Ethics and Governance");
        arrayList.add("CBMS503 /Investment Analysis and Portfolio Management");
        arrayList.add("CBMS504 /Commodity Derivative Market");
        arrayList.add("CBMS505 /Strategic Financial Management");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmsem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBMS601 /Operation Research");
        arrayList.add("CBMS602 /Corporate Communications and Public Relations");
        arrayList.add("CBMS603 /International Finance");
        arrayList.add("CBMS604 /Innovative Financial Services");
        arrayList.add("CBMS605 /Ris Management");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmmSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ABMM101 /Effective Communication Skill-I");
        arrayList.add("ABMM102 /Fundamental of Mass Communication");
        arrayList.add("ABMM103 /20th Century of History of the world and India");
        arrayList.add("ABMM104 /Introduction to Computers");
        arrayList.add("ABMM105 /Introduction to Economics");
        arrayList.add("ABMM106 /Introduction to Sociology");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmmSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ABMM201 /Effective Communication Skill II");
        arrayList.add("ABMM202 /Introduction to English Literature");
        arrayList.add("ABMM203 /Advanced Computers");
        arrayList.add("ABMM204 /Political Concepts and Indian Political System");
        arrayList.add("ABMM205 /Introduction to Psychology");
        arrayList.add("ABMM206 /Introduction to Marketing");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmmSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ABMM301 /Introduction to Public Relations");
        arrayList.add("ABMM302 /Introduction to Culture Studies");
        arrayList.add("ABMM303 /Introduction to Media Studies");
        arrayList.add("ABMM304 /Photography and Videography");
        arrayList.add("ABMM305 /Introduction to Creative Writing");
        arrayList.add("ABMM306 /Principles of Management");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmmSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ABMM401 /Introduction to Advertising");
        arrayList.add("ABMM402 /Introduction to Journalism");
        arrayList.add("ABMM403 /Radio and Television");
        arrayList.add("ABMM404 /Mass Media Research");
        arrayList.add("ABMM405 /Organisational Behaviour");
        arrayList.add("ABMM406 /Understanding Cinema");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmmSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ABMMA501 /Advertising Design");
        arrayList.add("ABMMA502 /Advertising and Marketing Research");
        arrayList.add("ABMMA503 /Brand Building");
        arrayList.add("ABMMA504 /Advertising in Contemporary Society");
        arrayList.add("ABMMJ501 /Reporting");
        arrayList.add("ABMMJ502 /Editing");
        arrayList.add("ABMMJ503 /Journalism and Public Opinion");
        arrayList.add("ABMMJ504 /Feature and Opinion");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bmmem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ABMMA601 /The Principles and Practice of Direct Marketing");
        arrayList.add("ABMMA602 /Agency Maanagement and Entrepreneurship");
        arrayList.add("ABMMA603 /Financial Management for Marketing and Advertising");
        arrayList.add("ABMMA604 /Legal Environment and Advertising Ethics");
        arrayList.add("ABMMJ601 /Press Law and Ethics");
        arrayList.add("ABMMJ602 /Issues in Global Media");
        arrayList.add("ABMMJ603 /Brodacast Journalism");
        arrayList.add("ABMMJ604 /Business and Magazine Journalism");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscitSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBIT101 /Imperative Programming");
        arrayList.add("SBIT102 /Digital Electronics");
        arrayList.add("SBIT103 /Operating Systems");
        arrayList.add("SBIT104 /Discrete Mathematics");
        arrayList.add("SBIT105 /Communication Skills");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscitSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBIT201 /Object Oriented Programming");
        arrayList.add("SBIT202 /Microprocessor Architecture");
        arrayList.add("SBIT203 /Web Programming");
        arrayList.add("SBIT204 /Applied Mathematics");
        arrayList.add("SBIT205 /Green Computings");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscitSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBIT301 /Advanced Python Programming");
        arrayList.add("SBIT302 /Applied Data Structures and Algorithm");
        arrayList.add("SBIT303 /Computer Netowrks");
        arrayList.add("SBIT304 /Databases and Transcations");
        arrayList.add("SBIT305 /Core Java with JSP");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscitSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBIT401 /Advanced Web Programming");
        arrayList.add("SBIT402 /Embedded System");
        arrayList.add("SBIT403 /Computer Oriented Numerical and Statistical Techniques");
        arrayList.add("SBIT404 /Software MEthodologies and Management");
        arrayList.add("SBIT405 /Advanced Networks and Security");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscitSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBIT501 /Software Project Management");
        arrayList.add("SBIT502 /Internet of Things");
        arrayList.add("SBIT503 /Advanced Web Programming");
        arrayList.add("SBIT504 /Linux System Administration");
        arrayList.add("SBIT505 /Enterprise Java");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bscitem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBIT601 /Software Quality Assurance");
        arrayList.add("SBIT602 /Security In Computing");
        arrayList.add("SBIT603 /Business Intelligence");
        arrayList.add("SBIT604 /Enterpirse Netowrking");
        arrayList.add("SBIT605 /Cyber Law");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocsdSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBSD101 /Communication Skill, Meet and Greet & Professional Etiquettes");
        arrayList.add("SBSD102 /French Language, Culture, Historical Milestone and Local Etiquettes");
        arrayList.add("SBSD103 /Office Automation");
        arrayList.add("SBSD104 /Web Designing and Programming");
        arrayList.add("SBSD105 /Logics and Algorithm");
        arrayList.add("SBSD106 /Software Engineering");
        arrayList.add("SBSD107 /Object Oriented Programming with C++");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocsdSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBSD201 /Organizational Behavior, Cultural and Health Psychology");
        arrayList.add("SBSD202 /Principles of Marketing and Customer Service Management");
        arrayList.add("SBSD203 /Introduction to Computer Networks");
        arrayList.add("SBSD204 /Modern Operating Systems");
        arrayList.add("SBSD205 /Computational Mathematics");
        arrayList.add("SBSD206 /Core Java");
        arrayList.add("SBSD207 /Database Management System");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocsdSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBSD301 /Business Communication");
        arrayList.add("SBSD302 /Digital Marketing & Public Relations & Advertising");
        arrayList.add("SBSD303 /Green Computing");
        arrayList.add("SBSD304 /Advanced Java");
        arrayList.add("SBSD305 /Advanced Web Designing and Programming");
        arrayList.add("SBSD306 /Data Communication and Networking");
        arrayList.add("SBSD307 /Software Testing");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocsdSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBSD401 /Basic of Book Keeping & Accounts");
        arrayList.add("SBSD402 /Principle of Management");
        arrayList.add("SBSD403 /Human Resource Management");
        arrayList.add("SBSD404 /Android App Development");
        arrayList.add("SBSD405 /C# & ASP.Net MVC");
        arrayList.add("SBSD406 /Computer Security");
        arrayList.add("SBSD407 /Advance SQL with Oracle");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocsdSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBSD501 /Strategic Manangement");
        arrayList.add("SBSD502 /Enterpreneurship & Business Planning-I");
        arrayList.add("SBSD503 /MultiMedia");
        arrayList.add("SBSD504 /Managerial Economics");
        arrayList.add("SBSD505 /Project Management");
        arrayList.add("SBSD506 /Python Programming and Data Structures");
        arrayList.add("SBSD507 /Big Data Analysis");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocsdSem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("SBSD601 /International Finance");
        arrayList.add("SBSD602 /Multimedia-II");
        arrayList.add("SBSD603 /Reasoning Aptitude and Placement Orientation");
        arrayList.add("SBSD604 /Economic Analyses and Data Analytics");
        arrayList.add("SBSD605 /Artificail Intelligence");
        arrayList.add("SBSD606 /Physical COmputing and Iot Programming");
        arrayList.add("SBSD607 /Emerging Technologies");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocttSem1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBTT101 /Communication Skills, Meet & Greet Professional Etiquettes");
        arrayList.add("CBTT102 /French Language, Culture, Historical milestones & local Etiquettes");
        arrayList.add("CBTT103 /Office Automation");
        arrayList.add("CBTT104 /History & Culture of the Inidan Subcontinent");
        arrayList.add("CBTT105 /Heritage and Tourism Resources");
        arrayList.add("CBTT106 /Toirosm Concepts & Principles");
        arrayList.add("CBTT107 /World Geography & International Tourist Circuits");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocttSem2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBTT201 /Organizational Behaviour");
        arrayList.add("CBTT202 /Principles of Marketing & Customer Service Management");
        arrayList.add("CBTT203 /Introduction to Computer Networks");
        arrayList.add("CBTT204 /Indian Geography & Tourism Products");
        arrayList.add("CBTT205 /Global Tourism-Industry and Issues");
        arrayList.add("CBTT206 /Travel Agency Management & Tour Guide Role");
        arrayList.add("CBTT207 /Reservations, E-Ticketing & Technology In Tourism Service");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocttSem3() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBTT301 /Business Communicatio");
        arrayList.add("CBTT302 /Green Computing");
        arrayList.add("CBTT303 /Digital Marketing, PR & Advertising");
        arrayList.add("CBTT304 /Tour Packaging");
        arrayList.add("CBTT305 /Sustainable Tourism");
        arrayList.add("CBTT306 /MICE");
        arrayList.add("CBTT307/ Art Styles, Cultural Expressions & Living traditions-Global & Indian");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocttSem4() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBTT401 /Report Writing");
        arrayList.add("CBTT402 /Green Computing");
        arrayList.add("CBTT403 /Human Resource Management");
        arrayList.add("CBTT404 /Digital Marketing");
        arrayList.add("CBTT405 /Tourism Economics");
        arrayList.add("CBTT406 /Destination Planning");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocttSem5() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBTT501 /Strategic Management");
        arrayList.add("CBTT502 /Multimedia");
        arrayList.add("CBTT503 /Enterpreneurship");
        arrayList.add("CBTT504 /Managerial Economics");
        arrayList.add("CBTT505 /International Tourism & Trends");
        arrayList.add("CBTT506 /Niche Tourism");
        arrayList.add("CBTT507 /Tour Manager Operations");
        arrayList.add("CBTT508 /Event Management");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void bvocttSem6() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CBTT601 /International Finance");
        arrayList.add("CBTT602 /Multimedia II");
        arrayList.add("CBTT603 /Reasoning Aptitude & Placement Orientation");
        arrayList.add("CBTT604 /Data Analytics");
        arrayList.add("CBTT605 /Quality Management in Tourism");
        arrayList.add("CBTT606 /Adventure Tourism");
        arrayList.add("CBTT607 /Tourism Law");
        arrayList.add("CBTT608 /Enterpreneurship In Tourism");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
//        _subjectDisplay.setText(_subCodeName.getSelectedItem().toString());
    }

    private void addDetails() {
        String teacherID = _lec_ug_teacherID.getText().toString();
        String name = _lec_ug_teacherName.getText().toString();

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

        lecture.setTeacherID(_lec_ug_teacherID.getText().toString().trim());
        lecture.setTeachersName(_lec_ug_teacherName.getText().toString().trim());
        lecture.setDepartment(_department.getSelectedItem().toString().trim());
        lecture.setYear(_semester.getSelectedItem().toString().trim());
        lecture.setTime(new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date()));
        lecture.setDate(DateFormat.getDateInstance(DateFormat.FULL).format(android.icu.util.Calendar.getInstance().getTime()));
        lecture.setSubCodeName(_subCodeName.getSelectedItem().toString().trim());

        _progressBarUG.setVisibility(View.VISIBLE);
        if (text1 != null && !text1.isEmpty()) {
            try {
                databaseReference1.child(firebaseAuth_lec_ug.getUid()).push().setValue(lecture);
                _progressBarUG.setVisibility(View.GONE);

            } catch (Exception e) {
                _progressBarUG.setVisibility(View.GONE);
                e.printStackTrace();
            }

            try {
                databaseReference.child(_department.getSelectedItem().toString())
                        .child(_semester.getSelectedItem().toString()).child(firebaseAuth_lec_ug.getUid()).push().setValue(lecture)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    _progressBarUG.setVisibility(View.GONE);
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
                                    _progressBarUG.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            } catch (Exception e) {
                _progressBarUG.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();


            }
        }
        else
        {
            _progressBarUG.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();
        }
    }
}
