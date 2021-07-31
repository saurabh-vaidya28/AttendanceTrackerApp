package com.example.attendancetracker.ui.studentdetails;

public class Student {

    private String Name,UID,Student_Phone,Parent_Email,Parent_Phone;
    String college,dept,year,semester;
    public Student() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getStudent_Phone() {
        return Student_Phone;
    }

    public void setStudent_Phone(String student_Phone) {
        Student_Phone = student_Phone;
    }

    public String getParent_Email() {
        return Parent_Email;
    }

    public void setParent_Email(String parent_Email) {
        Parent_Email = parent_Email;
    }

    public String getParent_Phone() {
        return Parent_Phone;
    }

    public void setParent_Phone(String parent_Phone) {
        Parent_Phone = parent_Phone;
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
