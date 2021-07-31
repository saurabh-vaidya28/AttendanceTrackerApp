package com.example.attendancetracker.admin.TeacherAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.attendancetracker.R;
import com.example.attendancetracker.admin.StudentAdmin.asdisplay;
import com.example.attendancetracker.tr;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class pgTeacher extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,databaseReference2;
    Spinner _stream;
    RadioGroup _year;
    RadioButton radioButtonUGYear;
    Button _view;
    ProgressBar _progressBarJC;
    String a,b,c,d;
    ListView lv;
    FirebaseDatabase database;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    asdisplay ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_teacher);


        _stream = findViewById(R.id.stream);
        lv=findViewById(R.id.list3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.postGraduate, R.layout.support_simple_spinner_dropdown_item);
        _stream.setAdapter(adapter);

        //display
        ad=new asdisplay();

        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.studentdisplay,R.id.userinfo,arrayList);


        //_year=radioGroup
        _year = findViewById(R.id.yearUG);
        _view = findViewById(R.id.view2);
        _progressBarJC = findViewById(R.id.progressBarJC);

        _view.setOnClickListener(this);
    }


    public void checkButtonUGYear(View v)
    {
        int radioIDYear=_year.getCheckedRadioButtonId();
        radioButtonUGYear=findViewById(radioIDYear);
        Toast.makeText(getApplicationContext(),"Selected Year: "+radioButtonUGYear.getText(),Toast.LENGTH_LONG).show();
    }


    public void view(){
        String item = _stream.getSelectedItem().toString().trim();
        if (item.equals("MSc-Chemistry")){
            try {
                switch (radioButtonUGYear.getId()) {
                    case R.id.fy:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Post Graduate").child("MSc-Chemistry").child("FY");
                        break;
                    case R.id.sy:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Post Graduate").child("MSc-Chemistry").child("SY");
                        break;

                }
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        }

        if (item.equals("Physics")){
            try {
                switch (radioButtonUGYear.getId()) {
                    case R.id.fy:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Post Graduate").child("Physics").child("FY");
                        break;
                    case R.id.sy:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Organised_Student_Details").child("Post Graduate").child("Physics").child("SY");
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
        if(v==_view){
            view();
            String spin=_stream.getSelectedItem().toString();
            String radio=((RadioButton)findViewById(_year.getCheckedRadioButtonId()))
                    .getText().toString();
            Intent intent=new Intent(pgTeacher.this, pgdspT.class);
            intent.putExtra("ug",spin);
            intent.putExtra("year",radio);
            startActivity(intent);
        }
    }
}

