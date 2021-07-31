package com.example.attendancetracker.Student.TrackAttendnce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetracker.R;
import com.example.attendancetracker.Student.Mark_Attendance_Database;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterAttendance extends RecyclerView.Adapter<MyAdapterAttendance.myViewHolder>  {

    private int itemCount;
    Context context;
    List<Mark_Attendance_Database> mark_attendance_databases;
//    private List<Mark_Attendance_Database> mark_attendance_databases_full;

//    MyAdapterLecture(List<Mark_Attendance_Database> mark_attendance_databases) {
//        this.mark_attendance_databases = mark_attendance_databases;
//        mark_attendance_databases_full = new ArrayList<>(mark_attendance_databases);
//    }
    public  MyAdapterAttendance(Context context)
    {
        this.mark_attendance_databases=new ArrayList<>();
//        mark_attendance_databases_full=new ArrayList<>(mark_attendance_databases);
        this.context=context;
    }

    public void addAll(List<Mark_Attendance_Database> new_mark_attendance_databases)
    {
        int initsize=mark_attendance_databases.size();
        mark_attendance_databases.addAll(new_mark_attendance_databases);
        notifyItemRangeChanged(initsize,new_mark_attendance_databases.size());
    }

    public String getLastItemId()
    {
        return mark_attendance_databases.get(mark_attendance_databases.size()-1).getUid();
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.card_view_attendance,parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.date.setText(mark_attendance_databases.get(position).getDate());
        holder.time.setText(mark_attendance_databases.get(position).getTime());
        holder.lec_details.setText(mark_attendance_databases.get(position).getLec_details());

    }

    @Override
    public int getItemCount() {
        return mark_attendance_databases.size();
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

        TextView date,time,lec_details;

        public myViewHolder(View itemView)
        {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.dateT);
            time=(TextView)itemView.findViewById(R.id.timeT);
            lec_details=(TextView)itemView.findViewById(R.id.lec_detailsT);


        }
    }
}
