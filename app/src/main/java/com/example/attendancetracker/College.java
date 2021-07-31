package com.example.attendancetracker;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class College {
    private  String college,dept,year,semester,subject;



    public College(String college, String dept, String year, String semester) {
        this.college = college;
        this.dept = dept;
        this.year = year;
        this.semester = semester;

    }

    public College(){

    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


}
