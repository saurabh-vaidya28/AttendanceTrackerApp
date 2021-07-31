package com.example.attendancetracker.ui.teacherlecturedetails;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Student.Mark_Attendance_Database;
import com.example.attendancetracker.Teacher.Lecture;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class LectureAdapter extends FirebaseRecyclerAdapter<Lecture, LectureAdapter.PastViewHolder> {

    public LectureAdapter(@NonNull FirebaseRecyclerOptions<Lecture> options) {
        super(options);
    }

    /*
    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, int i, @NonNull Mark_Attendance_Database mark_attendance_database) {
        holder._uid.setText(mark_attendance_database.getUid());
        holder._name.setText(mark_attendance_database.getName());
        holder._lec_details.setText(mark_attendance_database.getLec_details());
        holder._date.setText(mark_attendance_database.getDate());
        holder._time.setText(mark_attendance_database.getTime());
        holder._longi.setText(mark_attendance_database.getLongitude());
        holder._lati.setText(mark_attendance_database.getLatitude());


        String letter = "A";

        if(mark_attendance_database.getUid() != null && !mark_attendance_database.getUid().isEmpty()) {
            letter = mark_attendance_database.getUid().substring(0, 1);
        }

    }

*/

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, int position, @NonNull Lecture lectures) {
        holder._date.setText(lectures.getDate());
        holder._time.setText(lectures.getTime());
        holder._dept.setText(lectures.getDepartment());
        holder._year.setText(lectures.getYear());
        holder._sub_code_name.setText(lectures.getSubCodeName());

        String letter = "A";

        if(lectures.getTeacherID() != null && !lectures.getTeacherID().isEmpty()) {
            letter = lectures.getTeacherID().substring(0, 1);
        }
    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_lecture, parent, false);
        return new PastViewHolder(view);
    }

    class PastViewHolder extends RecyclerView.ViewHolder{

        TextView _date,_time,_sub_code_name,_dept,_year;
        Intent intent;
        public PastViewHolder(@NonNull View itemView) {
            super(itemView);

            _dept=itemView.findViewById(R.id.deptL);
            _year=itemView.findViewById(R.id.yearL);
            _sub_code_name = itemView.findViewById(R.id.subjectL);
            _date =itemView.findViewById(R.id.dateL);
            _time = itemView.findViewById(R.id.timeL);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    intent =  new Intent(view.getContext(), MarkAttendanceActivity.class);
//                    Log.d("Tag", "onClick：" + getAdapterPosition());
//                    intent.putExtra("uid",_uid.getText().toString());
//                    intent.putExtra("name",_name.getText().toString());
//                    intent.putExtra("lecture",_lec_details.getText().toString());
//                    intent.putExtra("date",_date.getText().toString());
//                    intent.putExtra("time",_time.getText().toString());
//                    intent.putExtra("longitude",_longi.getText().toString());
//                    intent.putExtra("latitude",_lati.getText().toString());
//                    view.getContext().startActivity(intent);
//                }
//            });
        }
    }
}
