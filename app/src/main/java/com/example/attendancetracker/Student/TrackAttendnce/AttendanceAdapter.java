package com.example.attendancetracker.Student.TrackAttendnce;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Student.MarkAttendanceActivity;
import com.example.attendancetracker.Student.Mark_Attendance_Database;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends FirebaseRecyclerAdapter<Mark_Attendance_Database,AttendanceAdapter.PastViewHolder> {

    Context context;
    List<Mark_Attendance_Database> mark_attendance_databases;

    public AttendanceAdapter(@NonNull FirebaseRecyclerOptions<Mark_Attendance_Database> options, Context context, List<Mark_Attendance_Database> mark_attendance_databases) {
        super(options);
        this.context = context;
        this.mark_attendance_databases = mark_attendance_databases;
    }

    public AttendanceAdapter(@NonNull FirebaseRecyclerOptions<Mark_Attendance_Database> options) {
        super(options);
    }

    public String getLastItemId()
    {
        return mark_attendance_databases.get(mark_attendance_databases.size()-1).getUid();
    }



    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, int i, @NonNull Mark_Attendance_Database mark_attendance_database) {
//        holder._uid.setText(mark_attendance_database.getUid());
//        holder._name.setText(mark_attendance_database.getName());
        holder._lec_details.setText(mark_attendance_database.getLec_details());
        holder._date.setText(mark_attendance_database.getDate());
        holder._time.setText(mark_attendance_database.getTime());
//        holder._longi.setText(mark_attendance_database.getLongitude());
//        holder._lati.setText(mark_attendance_database.getLatitude());


        String letter = "A";

        if(mark_attendance_database.getUid() != null && !mark_attendance_database.getUid().isEmpty()) {
            letter = mark_attendance_database.getUid().substring(0, 1);
        }

    }

    @Override
    public int getItemCount() {
        return  super.getItemCount();
    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_attendance, parent, false);
        return new PastViewHolder(view);
    }

    class PastViewHolder extends RecyclerView.ViewHolder{

        TextView _uid,_name,_lec_details,_date,_time,_longi,_lati;
        Intent intent;
        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
             _lec_details = itemView.findViewById(R.id.lec_detailsT);
            _date =itemView.findViewById(R.id.dateT);
            _time = itemView.findViewById(R.id.timeT);

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
