package com.example.attendancetracker.HOD;

public class LectureHOD {
    private String hodID,hodName,Date,Time,SubCodeName,Department,Year,Semester;

    public LectureHOD() {
    }

    public LectureHOD(String hodID, String hodName, String date, String time, String subCodeName, String department, String year, String semester) {
        this.hodID = hodID;
        this.hodName = hodName;
        Date = date;
        Time = time;
        SubCodeName = subCodeName;
        Department = department;
        Year = year;
        Semester = semester;
    }

    @Override
    public String toString() {
        return "LectureHOD{" +
                "hodID='" + hodID + '\'' +
                ", hodName='" + hodName + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                ", SubCodeName='" + SubCodeName + '\'' +
                ", Department='" + Department + '\'' +
                ", Year='" + Year + '\'' +
                ", Semester='" + Semester + '\'' +
                '}';
    }

    public String getHodID() {
        return hodID;
    }

    public void setHodID(String hodID) {
        this.hodID = hodID;
    }

    public String getHodName() {
        return hodName;
    }

    public void setHodName(String hodName) {
        this.hodName = hodName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSubCodeName() {
        return SubCodeName;
    }

    public void setSubCodeName(String subCodeName) {
        SubCodeName = subCodeName;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }
}
