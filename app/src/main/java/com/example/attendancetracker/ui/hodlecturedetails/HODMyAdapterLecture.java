package com.example.attendancetracker.ui.hodlecturedetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetracker.HOD.LectureHOD;
import com.example.attendancetracker.R;
import com.example.attendancetracker.Teacher.Lecture;

import java.util.ArrayList;
import java.util.List;

public class HODMyAdapterLecture extends RecyclerView.Adapter<HODMyAdapterLecture.myViewHolder>  {

    private int itemCount;
    Context context;
    List<LectureHOD> lecturesHod;
//    private List<Mark_Attendance_Database> mark_attendance_databases_full;

//    MyAdapterLecture(List<Mark_Attendance_Database> mark_attendance_databases) {
//        this.mark_attendance_databases = mark_attendance_databases;
//        mark_attendance_databases_full = new ArrayList<>(mark_attendance_databases);
//    }
    public HODMyAdapterLecture(Context context)
    {
        this.lecturesHod=new ArrayList<>();
//        mark_attendance_databases_full=new ArrayList<>(mark_attendance_databases);
        this.context=context;
    }

    public void addAll(List<LectureHOD> new_lectures)
    {
        int initsize=lecturesHod.size();
        lecturesHod.addAll(new_lectures);
        notifyItemRangeChanged(initsize,new_lectures.size());
    }

    public String getLastItemId()
    {
        return lecturesHod.get(lecturesHod.size()-1).getHodID();
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.card_view_lecture,parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.date.setText(lecturesHod.get(position).getDate());
        holder.time.setText(lecturesHod.get(position).getTime());
        holder.dept.setText(lecturesHod.get(position).getDepartment());
        holder.year.setText(lecturesHod.get(position).getYear());
        holder.sub_code_name.setText(lecturesHod.get(position).getSubCodeName());

    }

    @Override
    public int getItemCount() {
        return lecturesHod.size();
//        return itemCount;
    }

    /*
    @Override
    public Filter getFilter() {
        return mark_attendance_databases_Filter;
    }

    private Filter mark_attendance_databases_Filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Mark_Attendance_Database> filteredList=new ArrayList<>();

            if(constraint==null ||constraint.length()==0)
            {
                filteredList.addAll(mark_attendance_databases_full);
            }
            else{
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (Mark_Attendance_Database item:mark_attendance_databases_full)
                {
                    if(item.getDate().toLowerCase().contains(filterPattern) || item.getLec_details().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mark_attendance_databases.clear();
            mark_attendance_databases.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    */


    class myViewHolder extends RecyclerView.ViewHolder{

        TextView date,time,sub_code_name,dept,year;

        public myViewHolder(View itemView)
        {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.dateL);
            time=(TextView)itemView.findViewById(R.id.timeL);
            dept=(TextView)itemView.findViewById(R.id.deptL);
            year=(TextView)itemView.findViewById(R.id.yearL);
            sub_code_name=(TextView)itemView.findViewById(R.id.subjectL);
        }
    }
}
