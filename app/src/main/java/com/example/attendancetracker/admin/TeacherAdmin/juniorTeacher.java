package com.example.attendancetracker.admin.TeacherAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancetracker.R;
import com.example.attendancetracker.admin.StudentAdmin.asdisplay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class juniorTeacher extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Spinner _stream;
    RadioGroup _year;
    RadioButton radioButtonJCYear;
    Button _view;
    ProgressBar _progressBarJC;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    asdisplay ad;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junior_teacher);

        _stream = findViewById(R.id.stream);
        //  lv = findViewById(R.id.listj);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.junior, R.layout.support_simple_spinner_dropdown_item);
        _stream.setAdapter(adapter);


    /*    ad = new asdisplay();
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.studentdisplay, R.id.userinfo, arrayList);

     */


        //_year=radioGroup
        _year = findViewById(R.id.year);
        _view = findViewById(R.id.view);
        _progressBarJC = findViewById(R.id.progressBarJC);

        _view.setOnClickListener(this);


    }

    public void checkButtonYear(View v)
    {
        int radioIDYear=_year.getCheckedRadioButtonId();
        radioButtonJCYear=findViewById(radioIDYear);
        Toast.makeText(getApplicationContext(),"Selected Year: "+radioButtonJCYear.getText(),Toast.LENGTH_LONG).show();
    }



    public void view(){
        String item = _stream.getSelectedItem().toString().trim();
        if (item.equals("Science")) {
            try {
                switch (radioButtonJCYear.getId()) {
                    case R.id.fyjc:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Junior College").child("Science").child("FYJC");
                        break;
                    case R.id.syjc:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Junior College").child("Science").child("SYJC");
                        break;

                }
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
        if (item.equals("Commerce")) {
            try {
                switch (radioButtonJCYear.getId()) {
                    case R.id.fyjc:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Junior College").child("Commerce").child("FYJC");
                        break;
                    case R.id.syjc:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Junior College").child("Commerce").child("SYJC");
                        break;

                }
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
        if (item.equals("Arts")) {
            try {
                switch (radioButtonJCYear.getId()) {
                    case R.id.fyjc:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Junior College").child("Arts").child("FYJC");
                        break;
                    case R.id.syjc:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Junior College").child("Arts").child("SYJC");
                        break;

                }
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onClick(View v) {
        if (v == _view) {
            view();
            String spin=_stream.getSelectedItem().toString();
            String radio=((RadioButton)findViewById(_year.getCheckedRadioButtonId()))
                    .getText().toString();
            Intent intent=new Intent(juniorTeacher.this, judspT.class);
            intent.putExtra("ug",spin);
            intent.putExtra("year",radio);
            startActivity(intent);

        }
    }

}
