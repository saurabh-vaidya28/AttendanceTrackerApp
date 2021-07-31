package com.example.attendancetracker.Student;

public class Mark_Attendance_Database {
    private String name,uid,lec_details,time_date,time,college,semester,department,date;
    private String count;
    private String Latitude,Longitude;

    public Mark_Attendance_Database() {
    }

    public Mark_Attendance_Database(String name, String uid, String lec_details, String time_date, String time, String college, String semester, String department, String latitude, String longitude) {
        this.name = name;
        this.uid = uid;
        this.lec_details = lec_details;
        this.time_date = time_date;
        this.time = time;
        this.college = college;
        this.semester = semester;
        this.department = department;
        Latitude = latitude;
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "Mark_Attendance_Database{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", lec_details='" + lec_details + '\'' +
                ", time_date='" + time_date + '\'' +
                ", time='" + time + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", Longitude='" + Longitude + '\'' +
                '}';
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLec_details() {
        return lec_details;
    }

    public void setLec_details(String lec_details) {
        this.lec_details = lec_details;
    }

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
