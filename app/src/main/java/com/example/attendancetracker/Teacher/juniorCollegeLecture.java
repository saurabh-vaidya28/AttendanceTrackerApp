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

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class juniorCollegeLecture extends AppCompatActivity implements View.OnClickListener{

    private TextView _lec_jc_teacherID,_lec_jc_teacherName;
    Spinner _stream;
    RadioGroup _year,_div;
    RadioButton radioButtonJCYear,radioButtonJCDiv;
    EditText _teacherID,_name,_subjectName,_subjectCode;
    Button _date,_startTime,_endTime,_generate,_clear;
    TextView _textDate,_textStartTime,_textEndTime;
    ImageView _qrCode;
    private FirebaseAuth firebaseAuth_lec_jc;
    DatabaseReference databaseReference,databaseReference_lec_jc;
    Lecture lecture;
    private ProgressBar _progressBarJC;
    long maxId=0;
    DatePickerDialog.OnDateSetListener mOnDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junior_college_lecture);
        setTitle("Lecture Instance");

        _lec_jc_teacherID=(TextView)findViewById(R.id.lec_teacherID_jc);
        _lec_jc_teacherName=(TextView)findViewById(R.id.lec_teacherName_jc);

        _stream=(Spinner)findViewById(R.id.stream);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.junior,R.layout.support_simple_spinner_dropdown_item);
        _stream.setAdapter(adapter);

        //_year=radioGroup
        _year=(RadioGroup)findViewById(R.id.year);
        _div=(RadioGroup)findViewById(R.id.div);

        _progressBarJC=(ProgressBar)findViewById(R.id.progressBarJC);
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
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        
        lecture=new Lecture();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Lecture").child("Junior College");

        firebaseAuth_lec_jc=FirebaseAuth.getInstance();
        databaseReference_lec_jc=FirebaseDatabase.getInstance().getReference().child("Teacher_Details").child(firebaseAuth_lec_jc.getUid());

        databaseReference_lec_jc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lec_jc_Code=dataSnapshot.child("empCode_of_Teacher").getValue().toString();
                _lec_jc_teacherID.setText(lec_jc_Code);
                String lec_jc_Name=dataSnapshot.child("name_of_Teacher").getValue().toString();
                _lec_jc_teacherName.setText(lec_jc_Name);
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

    public void checkButtonYear(View v)
    {
        int radioIDYear=_year.getCheckedRadioButtonId();
        radioButtonJCYear=(RadioButton)findViewById(radioIDYear);
        Toast.makeText(this,"Selected Year: "+radioButtonJCYear.getText(),Toast.LENGTH_LONG).show();
    }

    public void checkButtonDivision(View v)
    {
        int radioIDDiv=_div.getCheckedRadioButtonId();
        radioButtonJCDiv=(RadioButton)findViewById(radioIDDiv);

        Toast.makeText(this,"Selected Division: "+radioButtonJCDiv.getText(),Toast.LENGTH_LONG).show();
    }

    private void Generate() {

        String item = _stream.getSelectedItem().toString();
        String teacherID = _lec_jc_teacherID.getText().toString();
        String name = _lec_jc_teacherName.getText().toString();
        String subjectName = _subjectName.getText().toString();
        String subjectCode = _subjectCode.getText().toString();

//        String date = _textDate.getText().toString();
//        String st = _textStartTime.getText().toString();
//        String et = _textEndTime.getText().toString();

//        String text = teacherID + "\n" + name + "\n" + subjectName + "\n" + subjectCode + "\n" + date + "\n" + st + "\n" + et;
        String text=teacherID + "\n" + name + "\n" +subjectName+"\n"+subjectCode;

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


        if (item.equals("Arts") || item.equals("Commerce") || item.equals("Science")) {
            if (radioButtonJCYear.getText().equals("FYJC")) {
                if (text != null && !text.isEmpty()) {
                    _progressBarJC.setVisibility(View.VISIBLE);
                    try {
                        _progressBarJC.setVisibility(View.GONE);
                        lecture.setTeacherID(_lec_jc_teacherID.getText().toString().trim());
                        lecture.setTeachersName(_lec_jc_teacherName.getText().toString().trim());
                        lecture.setSubjectName(_subjectName.getText().toString().trim());
                        lecture.setSubjectCode(_subjectCode.getText().toString().trim());
//                        lecture.setTime_date(new SimpleDateFormat("EEEE , dd-MM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime()));
//                        lecture.setDate(_textDate.getText().toString().trim());
//                        lecture.setStartTime(_textStartTime.getText().toString().trim());
//                        lecture.setEndTime(_textEndTime.getText().toString().trim());

                        switch (radioButtonJCDiv.getId()) {
                            case R.id.a:
                                databaseReference.child(item).child("FYJC").child("Div-A").push().setValue(lecture);
                                break;
                            case R.id.b:
                                databaseReference.child(item).child("FYJC").child("Div-B").push().setValue(lecture);
                                break;
                            case R.id.c:
                                databaseReference.child(item).child("FYJC").child("Div-C").push().setValue(lecture);
                                break;
                            case R.id.d:
                                databaseReference.child(item).child("FYJC").child("Div-D").push().setValue(lecture);
                                break;
                        }
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        _qrCode.setImageBitmap(bitmap);
                        Toast.makeText(juniorCollegeLecture.this, "Lecture instance created successfully", Toast.LENGTH_LONG).show();

                    } catch (WriterException e) {
                        _progressBarJC.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(juniorCollegeLecture.this, "Lecture instance not created", Toast.LENGTH_LONG).show();
                    }
                }


            }
            else if (radioButtonJCYear.getText().equals("SYJC")) {
                if (text != null && !text.isEmpty()) {
                    _progressBarJC.setVisibility(View.VISIBLE);
                    try {
                        _progressBarJC.setVisibility(View.GONE);
                        lecture.setTeacherID(_lec_jc_teacherID.getText().toString().trim());
                        lecture.setTeachersName(_lec_jc_teacherName.getText().toString().trim());
                        lecture.setSubjectName(_subjectName.getText().toString().trim());
                        lecture.setSubjectCode(_subjectCode.getText().toString().trim());
//                        lecture.setTime_date(new SimpleDateFormat("EEEE , dd-MM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime()));
//                        lecture.setDate(_textDate.getText().toString().trim());
//                        lecture.setStartTime(_textStartTime.getText().toString().trim());
//                        lecture.setEndTime(_textEndTime.getText().toString().trim());

                        switch (radioButtonJCDiv.getId()) {
                            case R.id.a:
                                databaseReference.child(item).child("SYJC").child("Div-A").push().setValue(lecture);
                                break;
                            case R.id.b:
                                databaseReference.child(item).child("SYJC").child("Div-B").push().setValue(lecture);
                                break;
                            case R.id.c:
                                databaseReference.child(item).child("SYJC").child("Div-C").push().setValue(lecture);
                                break;
                            case R.id.d:
                                databaseReference.child(item).child("SYJC").child("Div-D").push().setValue(lecture);
                                break;
                        }
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        _qrCode.setImageBitmap(bitmap);
                        Toast.makeText(juniorCollegeLecture.this, "Lecture instance created successfully", Toast.LENGTH_LONG).show();

                    } catch (WriterException e) {
                        _progressBarJC.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(juniorCollegeLecture.this, "Lecture instance not created", Toast.LENGTH_LONG).show();
                    }
                }


            } else {
                Toast.makeText(juniorCollegeLecture.this, "Year or Semester not selected!!!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(juniorCollegeLecture.this, "Please select department!!!", Toast.LENGTH_LONG).show();
        }
    }



    private void Clear()
    {
//        _teacherID.setText("");
//        _name.setText("");
        _subjectName.setText("");
        _subjectCode.setText("");
//        _textDate.setText("");
//        _textStartTime.setText("");
//        _textEndTime.setText("");
        _qrCode.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {

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
