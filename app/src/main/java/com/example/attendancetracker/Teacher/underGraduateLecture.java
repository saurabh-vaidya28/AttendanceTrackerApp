package com.example.attendancetracker.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class underGraduateLecture extends AppCompatActivity implements View.OnClickListener{

    private TextView _lec_ug_teacherID,_lec_ug_teacherName;
    Spinner _department;
    RadioGroup _yearUG,_semUG;
    RadioButton radioButtonUGYear,radioButtonUGSem;
    EditText _teacherID,_name,_subjectName,_subjectCode;
    Button _date,_startTime,_endTime,_generate,_clear;
    TextView _textDate,_textStartTime,_textEndTime;
    ImageView _qrCode;
    private FirebaseAuth firebaseAuth_lec_ug;
    DatabaseReference databaseReference,databaseReference_lec_ug;
    Lecture lecture;
    private ProgressBar _progressBarUG;
    long maxId=0;
    DatePickerDialog.OnDateSetListener mOnDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_graduate_lecture);

        _lec_ug_teacherID=(TextView)findViewById(R.id.lec_teacherID_ug);
        _lec_ug_teacherName=(TextView)findViewById(R.id.lec_teacherName_ug);

        _department=(Spinner)findViewById(R.id.department);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.underGraduate,R.layout.support_simple_spinner_dropdown_item);
        _department.setAdapter(adapter);

        //_year=radioGroup
        _yearUG=(RadioGroup)findViewById(R.id.yearUG);
        _semUG=(RadioGroup)findViewById(R.id.semUG);

        _progressBarUG=(ProgressBar)findViewById(R.id.progressBarUG);
//        _teacherID=(EditText)findViewById(R.id.teacherID);
//        _name=(EditText)findViewById(R.id.name);
        _subjectName=(EditText)findViewById(R.id.subjectName);
        _subjectCode=(EditText)findViewById(R.id.subjectCode);

//        _date=(Button) findViewById(R.id.date);
//        _startTime=(Button)findViewById(R.id.startTime);
//        _endTime=(Button) findViewById(R.id.endTime);
        _generate=(Button)findViewById(R.id.generate);
        _clear=(Button)findViewById(R.id.clear);
        _qrCode=(ImageView)findViewById(R.id.qrCode);
//        _textDate=(TextView)findViewById(R.id.textDate);
//        _textStartTime=(TextView)findViewById(R.id.textStartTime) ;
//        _textEndTime=(TextView)findViewById(R.id.textEndTime);

        //for date
        Calendar calendar=Calendar.getInstance();
        String currentDate= java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.getTime());

        lecture=new Lecture();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Lecture").child("Under-Graduate");

        firebaseAuth_lec_ug= FirebaseAuth.getInstance();
        databaseReference_lec_ug=FirebaseDatabase.getInstance().getReference().child("Teacher_Details").child(firebaseAuth_lec_ug.getUid());
        databaseReference_lec_ug.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lec_jc_Code=dataSnapshot.child("empCode_of_Teacher").getValue().toString();
                _lec_ug_teacherID.setText(lec_jc_Code);
                String lec_jc_Name=dataSnapshot.child("name_of_Teacher").getValue().toString();
                _lec_ug_teacherName.setText(lec_jc_Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        _date.setOnClickListener(this);
//        _startTime.setOnClickListener(this);
//        _endTime.setOnClickListener(this);
        _generate.setOnClickListener(this);
        _clear.setOnClickListener(this);

    }

    public void checkButtonUGYear(View v)
    {
        int radioID=_yearUG.getCheckedRadioButtonId();
        radioButtonUGYear=(RadioButton)findViewById(radioID);

        Toast.makeText(this,"Selected Year: "+radioButtonUGYear.getText(),Toast.LENGTH_LONG).show();
    }

    public void checkButtonUGSem(View v)
    {
        int radioID=_semUG.getCheckedRadioButtonId();
        radioButtonUGSem=(RadioButton)findViewById(radioID);

        Toast.makeText(this,"Selected Year: "+radioButtonUGSem.getText(),Toast.LENGTH_LONG).show();
    }
//    private void DatePicker()
//    {
//        Calendar calendar=Calendar.getInstance();
//
//        int YEAR=calendar.get(Calendar.YEAR);
//        int MONTH=calendar.get(Calendar.MONTH);
//        int DATE=calendar.get(Calendar.DATE);
//
//        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
////                String dateString=year+" "+month+" "+dayOfMonth;
////                _textDate.setText(dateString);
//
//                Calendar calendar1=Calendar.getInstance();
//                calendar1.set(Calendar.YEAR,year);
//                calendar1.set(Calendar.MONTH,month);
//                calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//
//                CharSequence charSequence= DateFormat.format("EEEE, MMM d, yyyy",calendar1);
//                _textDate.setText(charSequence);
//            }
//        }, YEAR, MONTH, DATE);
//        datePickerDialog.show();
//    }
//
//    private void StartTime()
//    {
//        Calendar calendar=Calendar.getInstance();
//        int HOUR=calendar.get(Calendar.HOUR);
//        int MINUTE=calendar.get(Calendar.MINUTE);
//
//        boolean is24HourFormat= DateFormat.is24HourFormat(this);
//
//        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                //    String startTime="Hour:"+hourOfDay+" Minute:"+minute;
//                //    _textStartTime.setText(startTime);
//
//                Calendar calendar1=Calendar.getInstance();
//                calendar1.set(Calendar.HOUR,hourOfDay);
//                calendar1.set(Calendar.MINUTE,minute);
//
//                CharSequence charSequence=DateFormat.format("hh:mm a",calendar1);
//                _textStartTime.setText(charSequence);
//            }
//        },HOUR,MINUTE,is24HourFormat);
//        timePickerDialog.show();
//    }
//
//    private void EndTime()
//    {
//        Calendar calendar=Calendar.getInstance();
//        int HOUR=calendar.get(Calendar.HOUR);
//        int MINUTE=calendar.get(Calendar.MINUTE);
//
//        boolean is24HourFormat= DateFormat.is24HourFormat(this);
//
//        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
////                String endTime="Hour:"+hourOfDay+" Minute:"+minute;
////                _textEndTime.setText(endTime);
//                Calendar calendar1=Calendar.getInstance();
//                calendar1.set(Calendar.HOUR,hourOfDay);
//                calendar1.set(Calendar.MINUTE,minute);
//
//                CharSequence charSequence=DateFormat.format("hh:mm a",calendar1);
//                _textEndTime.setText(charSequence);
//            }
//        },HOUR,MINUTE,is24HourFormat);
//        timePickerDialog.show();
//    }

    private void Clear()
    {
        _subjectName.setText("");
        _subjectCode.setText("");
        _qrCode.setVisibility(View.INVISIBLE);
    }

    private void Generate() {

        String item = _department.getSelectedItem().toString();
        String teacherID = _lec_ug_teacherID.getText().toString();
        String name = _lec_ug_teacherName.getText().toString();
        String subjectName = _subjectName.getText().toString();
        String subjectCode = _subjectCode.getText().toString();
//        String date = _textDate.getText().toString();
//        String st = _textStartTime.getText().toString();
//        String et = _textEndTime.getText().toString();

//        String text = teacherID + "\n" + name + "\n" + subjectName + "\n" + subjectCode + "\n" + date + "\n" + st + "\n" + et;
        String text = teacherID + "\n" + name + "\n" + subjectName + "\n" + subjectCode;

//        if (TextUtils.isEmpty(teacherID)) {
//            Toast.makeText(this, "Please enter teacher's id", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(name)) {
//            Toast.makeText(this, "Please enter teacher's name", Toast.LENGTH_LONG).show();
//            return;
//        }

        if (TextUtils.isEmpty(subjectName)) {
            Toast.makeText(this, "Please enter subject name", Toast.LENGTH_LONG).show();
            return;
        }

        //checking for mobile no.
        if (TextUtils.isEmpty(subjectCode)) {
            Toast.makeText(this, "Please enter valid subject code", Toast.LENGTH_LONG).show();
            return;
        }

//        //checking if email and passwords are empty
//        if (TextUtils.isEmpty(date)) {
//            Toast.makeText(this, "Please enter date", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(st)) {
//            Toast.makeText(this, "Please enter start time", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(et)) {
//            Toast.makeText(this, "Please enter end time", Toast.LENGTH_LONG).show();
//            return;
//        }

        if(item.equals("BCom") || item.equals("BA") || item.equals("BSc") || item.equals("BBI") || item.equals("BAF") || item.equals("BFM") || item.equals("BMS") || item.equals("BMM") || item.equals("BSc-IT") || item.equals("BVoc-SD") || item.equals("BVoc-TT"))
        {
            if (radioButtonUGYear.getText().equals("FY")) {
                if (text != null && !text.isEmpty()) {
                    _progressBarUG.setVisibility(View.VISIBLE);
                    try {
                        _progressBarUG.setVisibility(View.GONE);
                        lecture.setTeacherID(_lec_ug_teacherID.getText().toString().trim());
                        lecture.setTeachersName(_lec_ug_teacherName.getText().toString().trim());
                        lecture.setSubjectName(_subjectName.getText().toString().trim());
                        lecture.setSubjectCode(_subjectCode.getText().toString().trim());
//                        lecture.setTime_date(new SimpleDateFormat("EEEE , dd-MM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime()));
//                        lecture.setDate(_textDate.getText().toString().trim());
//                        lecture.setStartTime(_textStartTime.getText().toString().trim());
//                        lecture.setEndTime(_textEndTime.getText().toString().trim());

                        switch (radioButtonUGSem.getId()) {
                            case R.id.sem1:
                                databaseReference.child(item).child("FY").child("Sem1").push().setValue(lecture);
                                break;
                            case R.id.sem2:
                                databaseReference.child(item).child("FY").child("Sem2").push().setValue(lecture);
                                break;
                            case R.id.sem3:
                                databaseReference.child(item).child("FY").child("Sem3").push().setValue(lecture);
                                break;
                            case R.id.sem4:
                                databaseReference.child(item).child("FY").child("Sem4").push().setValue(lecture);
                                break;
                            case R.id.sem5:
                                databaseReference.child(item).child("FY").child("Sem5").push().setValue(lecture);
                                break;
                            case R.id.sem6:
                                databaseReference.child(item).child("FY").child("Sem6").push().setValue(lecture);
                                break;
                        }
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        _qrCode.setImageBitmap(bitmap);
                        Toast.makeText(underGraduateLecture.this, "Lecture instance created successfully", Toast.LENGTH_LONG).show();

                    } catch (WriterException e) {
                        _progressBarUG.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(underGraduateLecture.this, "Lecture instance not created", Toast.LENGTH_LONG).show();
                    }
                }


            }
            else if (radioButtonUGYear.getText().equals("SY")) {
                if (text != null && !text.isEmpty()) {
                    _progressBarUG.setVisibility(View.VISIBLE);
                    try {
                        _progressBarUG.setVisibility(View.GONE);
                        lecture.setTeacherID(_lec_ug_teacherID.getText().toString().trim());
                        lecture.setTeachersName(_lec_ug_teacherName.getText().toString().trim());
                        lecture.setSubjectName(_subjectName.getText().toString().trim());
                        lecture.setSubjectCode(_subjectCode.getText().toString().trim());
//                        lecture.setTime_date(new SimpleDateFormat("EEEE , dd-MM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime()));
//                        lecture.setDate(_textDate.getText().toString().trim());
//                        lecture.setStartTime(_textStartTime.getText().toString().trim());
//                        lecture.setEndTime(_textEndTime.getText().toString().trim());

                        switch (radioButtonUGSem.getId()) {
                            case R.id.sem1:
                                databaseReference.child(item).child("SY").child("Sem1").push().setValue(lecture);
                                break;
                            case R.id.sem2:
                                databaseReference.child(item).child("SY").child("Sem2").push().setValue(lecture);
                                break;
                            case R.id.sem3:
                                databaseReference.child(item).child("SY").child("Sem3").push().setValue(lecture);
                                break;
                            case R.id.sem4:
                                databaseReference.child(item).child("SY").child("Sem4").push().setValue(lecture);
                                break;
                            case R.id.sem5:
                                databaseReference.child(item).child("SY").child("Sem5").push().setValue(lecture);
                                break;
                            case R.id.sem6:
                                databaseReference.child(item).child("SY").child("Sem6").push().setValue(lecture);
                                break;
                        }
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        _qrCode.setImageBitmap(bitmap);
                        Toast.makeText(underGraduateLecture.this, "Lecture instance created successfully", Toast.LENGTH_LONG).show();

                    } catch (WriterException e) {
                        _progressBarUG.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(underGraduateLecture.this, "Lecture instance not created", Toast.LENGTH_LONG).show();
                    }
                }


            }
            else if (radioButtonUGYear.getText().equals("TY")) {
                if (text != null && !text.isEmpty()) {
                    _progressBarUG.setVisibility(View.VISIBLE);
                    try {
                        _progressBarUG.setVisibility(View.GONE);
                        lecture.setTeacherID(_lec_ug_teacherID.getText().toString().trim());
                        lecture.setTeachersName(_lec_ug_teacherName.getText().toString().trim());
                        lecture.setSubjectName(_subjectName.getText().toString().trim());
                        lecture.setSubjectCode(_subjectCode.getText().toString().trim());
//                        lecture.setTime_date(new SimpleDateFormat("EEEE , dd-MM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime()));
//                        lecture.setDate(_textDate.getText().toString().trim());
//                        lecture.setStartTime(_textStartTime.getText().toString().trim());
//                        lecture.setEndTime(_textEndTime.getText().toString().trim());

                        switch (radioButtonUGSem.getId()) {
                            case R.id.sem1:
                                databaseReference.child(item).child("TY").child("Sem1").push().setValue(lecture);
                                break;
                            case R.id.sem2:
                                databaseReference.child(item).child("TY").child("Sem2").push().setValue(lecture);
                                break;
                            case R.id.sem3:
                                databaseReference.child(item).child("TY").child("Sem3").push().setValue(lecture);
                                break;
                            case R.id.sem4:
                                databaseReference.child(item).child("TY").child("Sem4").push().setValue(lecture);
                                break;
                            case R.id.sem5:
                                databaseReference.child(item).child("TY").child("Sem5").push().setValue(lecture);
                                break;
                            case R.id.sem6:
                                databaseReference.child(item).child("TY").child("Sem6").push().setValue(lecture);
                                break;
                        }
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        _qrCode.setImageBitmap(bitmap);
                        Toast.makeText(underGraduateLecture.this, "Lecture instance created successfully", Toast.LENGTH_LONG).show();

                    } catch (WriterException e) {
                        _progressBarUG.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(underGraduateLecture.this, "Lecture instance not created", Toast.LENGTH_LONG).show();
                    }
                }


            }
            else
            {
                Toast.makeText(underGraduateLecture.this, "Year or Semester not selected!!!", Toast.LENGTH_LONG).show();
            }
        }

        else
        {
            Toast.makeText(underGraduateLecture.this, "Please select department!!!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
//        if(v==_date)
//        {
//            DatePicker();
//        }
//
//        if(v==_startTime)
//        {
//            StartTime();
//        }
//
//        if(v==_endTime)
//        {
//            EndTime();
//        }
//

        if(v==_generate)
        {
            Generate();
            closeKeyboard();
        }

        if(v==_clear)
        {
            Clear();
        }
    }

    private void closeKeyboard()
    {
        View view=this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}