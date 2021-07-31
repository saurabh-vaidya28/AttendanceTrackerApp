package com.example.attendancetracker.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Student.MarkAttendanceActivity;
import com.example.attendancetracker.ui.base.BaseDrawerActivity;
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

public class JuniorCollege1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView _lec_jc_teacherID, _lec_jc_teacherName;
//    private String text1;
    private SearchableSpinner _stream;
    private SearchableSpinner _year;
    private SearchableSpinner _subCodeName;
    RadioGroup _div;
    RadioButton radioButtonJCDiv;
    Button _generate, _clear;
    ImageView _qrCode;
    private FirebaseAuth firebaseAuth_lec_jc;
    DatabaseReference databaseReference,databaseReference1, databaseReference_lec_jc;
    Lecture lecture;
    private ProgressBar _progressBarJC;

    public String[] stream = new String[]{"Please select stream", "Arts", "Commerce", "Science"};
    public String[] year = new String[]{"Please select year","FYJC","SYJC"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junior_college1);
        setTitle("Lecture Instance");

        _lec_jc_teacherID = (TextView) findViewById(R.id.lec_teacherID_jc);
        _lec_jc_teacherName = (TextView) findViewById(R.id.lec_teacherName_jc);

        _stream = (SearchableSpinner) findViewById(R.id.stream);
        _year = (SearchableSpinner) findViewById(R.id.year);
        _subCodeName = (SearchableSpinner) findViewById(R.id.subCodeName);
//        _semester = (SearchableSpinner)findViewById(R.id.semester);

        final String a;
        a = (String) _stream.getSelectedItem();
        ArrayAdapter<CharSequence> adaptera = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Stream, R.layout.support_simple_spinner_dropdown_item);
        _stream.setAdapter(adaptera);

        final String b;
        b = (String) _year.getSelectedItem();
        ArrayAdapter<CharSequence> adapterb = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Year, R.layout.support_simple_spinner_dropdown_item);
        _year.setAdapter(adapterb);

        //_div=radioGroup
        _div = (RadioGroup) findViewById(R.id.div);

        _progressBarJC = (ProgressBar) findViewById(R.id.progressBarJC);

        _generate = (Button) findViewById(R.id.generate);
//        _clear = (Button) findViewById(R.id.clear);
        _qrCode = (ImageView) findViewById(R.id.qrCode);

        //for date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        lecture = new Lecture();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lecture-Teacher").child("Junior College");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Lecture_Combined-Teacher").child("Junior_College");

        firebaseAuth_lec_jc = FirebaseAuth.getInstance();
        databaseReference_lec_jc = FirebaseDatabase.getInstance().getReference().child("Teacher_Details").child(firebaseAuth_lec_jc.getUid());

        databaseReference_lec_jc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lec_jc_Code = dataSnapshot.child("empCode_of_Teacher").getValue().toString();
                _lec_jc_teacherID.setText(lec_jc_Code);
                String lec_jc_Name = dataSnapshot.child("name_of_Teacher").getValue().toString();
                _lec_jc_teacherName.setText(lec_jc_Name);
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

        _stream.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stream);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _stream.setAdapter(adapter);

        _year.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, year);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _year.setAdapter(adapter1);

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
        a = (String) _stream.getSelectedItem();
        b=(String)_year.getSelectedItem();
        if (a.equals("Please select stream") && b.equals("Please select year")) {
            empty();
        } else if (a.equals("Arts") && b.equals("FYJC")) {
            artsFY();
        } else if (a.equals("Commerce") && b.equals("FYJC")) {
            commerceFY();

        } else if(a.equals("Science") && b.equals("FYJC")){
            scienceFY();
        }
        else if (a.equals("Arts") && b.equals("SYJC")) {
            artsSY();
        } else if (a.equals("Commerce") && b.equals("SYJC")) {
            commerceSY();

        } else{
            scienceSY();
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

    private void artsFY() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Economics");
        arrayList.add("English");
        arrayList.add("French");
        arrayList.add("Gujarati");
        arrayList.add("Hindi");
        arrayList.add("History");
        arrayList.add("Marathi");
        arrayList.add("Philosopy");
        arrayList.add("Political Science");
        arrayList.add("Psychology");
        arrayList.add("Sindhi");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
    }

    private void artsSY() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Economics");
        arrayList.add("English");
        arrayList.add("Hindi");
        arrayList.add("History");
        arrayList.add("Marathi");
        arrayList.add("Philosopy");
        arrayList.add("Political Science");
        arrayList.add("Psychology");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
    }

    private void commerceFY() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Secretarial  Practice");
        arrayList.add("Organisation of Commerce");
        arrayList.add("Book Keeping & Accountancy");
        arrayList.add("Economics");
        arrayList.add("EVS");
        arrayList.add("Mathematics");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
    }

    private void commerceSY() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Economics");
        arrayList.add("EVS");
        arrayList.add("Mathematics");
        arrayList.add("Secretarial  Practice");
        arrayList.add("Organisation of Commerce");
        arrayList.add("Book Keeping & Accountancy");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
    }

    private void scienceFY() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Biology");
        arrayList.add("Chemistry");
        arrayList.add("Computer Science");
        arrayList.add("Environmental Science");
        arrayList.add("Mathematics");
        arrayList.add("Science");
        arrayList.add("Marathi");
        arrayList.add("Hindi");
        arrayList.add("Psychology");
        arrayList.add("Sindhi");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
    }

    private void scienceSY() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Environmental Science");
        arrayList.add("Mathematics");
        arrayList.add("Biology");
        arrayList.add("Chemistry");
        arrayList.add("Computer Science");
        arrayList.add("Marathi");
        arrayList.add("Hindi");
        arrayList.add("Psychology");
        arrayList.add("Sindhi");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        _subCodeName.setAdapter(arrayAdapter);
    }

    public void checkButtonDivision(View v)
    {
        int radioIDDiv=_div.getCheckedRadioButtonId();
        radioButtonJCDiv=(RadioButton)findViewById(radioIDDiv);

        Toast.makeText(this,"Selected Division: "+radioButtonJCDiv.getText(),Toast.LENGTH_LONG).show();
    }

    private void addDetails() {
        String teacherID = _lec_jc_teacherID.getText().toString();
        String name = _lec_jc_teacherName.getText().toString();

        String stream = (String) _stream.getSelectedItem();
        String yr = (String) _year.getSelectedItem();
        String s_c_n = (String) _subCodeName.getSelectedItem();

        final String text1 = teacherID + "\n" + name + "\n" + s_c_n;

        if (_stream.getSelectedItem().toString().equals("Please select stream")) {
            Toast.makeText(getApplicationContext(), "Please select stream", Toast.LENGTH_LONG).show();
            return;
        }
        if (_year.getSelectedItem().toString().equals("Please select year")){
            Toast.makeText(getApplicationContext(), "Please select year", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(s_c_n)) {
            Toast.makeText(getApplicationContext(), "Please select subject", Toast.LENGTH_LONG).show();
            return;
        }

        lecture.setTeacherID(_lec_jc_teacherID.getText().toString().trim());
        lecture.setTeachersName(_lec_jc_teacherName.getText().toString().trim());
        lecture.setDepartment(_stream.getSelectedItem().toString().trim());
        lecture.setYear(_year.getSelectedItem().toString().trim());
        lecture.setSubCodeName(_subCodeName.getSelectedItem().toString().trim());
        lecture.setTime(new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date()));
        lecture.setDate(DateFormat.getDateInstance(DateFormat.FULL).format(android.icu.util.Calendar.getInstance().getTime()));

        _progressBarJC.setVisibility(View.VISIBLE);
            if (text1 != null && !text1.isEmpty()) {
                try {
                    databaseReference1.child(firebaseAuth_lec_jc.getUid()).push().setValue(lecture);
                    _progressBarJC.setVisibility(View.GONE);

                } catch (Exception e) {
                    _progressBarJC.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                try {
                    databaseReference.child(_stream.getSelectedItem().toString())
                            .child(_year.getSelectedItem().toString()).child(radioButtonJCDiv.getText().toString()).child(firebaseAuth_lec_jc.getUid()).push().setValue(lecture)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        _progressBarJC.setVisibility(View.GONE);
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
                                        _progressBarJC.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                } catch (Exception e) {
                    _progressBarJC.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();


            }
        }
        else
        {
            _progressBarJC.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Lecture instance not created", Toast.LENGTH_LONG).show();
        }
    }
}