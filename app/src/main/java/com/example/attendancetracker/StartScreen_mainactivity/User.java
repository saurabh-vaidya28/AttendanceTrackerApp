package com.example.attendancetracker.StartScreen_mainactivity;


public class User {
    String HOD,Teacher,Student,Parents;

    public User(){

    }

    public User(String HOD, String teacher, String student, String parents) {
        this.HOD = HOD;
        Teacher = teacher;
        Student = student;
        Parents = parents;
    }

    public String getHOD() {
        return HOD;
    }

    public void setHOD(String HOD) {
        this.HOD = HOD;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getStudent() {
        return Student;
    }

    public void setStudent(String student) {
        Student = student;
    }

    public String getParents() {
        return Parents;
    }

    public void setParents(String parents) {
        Parents = parents;
    }
}

