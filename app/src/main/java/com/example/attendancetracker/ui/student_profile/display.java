package com.example.attendancetracker.ui.student_profile;

public class display {
    String Name,EmpCode,Phone,Rollno;

    display(){

    }

    public display(String name, String empCode, String phone, String rollno) {
        Name = name;
        EmpCode = empCode;
        Phone = phone;
        Rollno = rollno;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmpCode() {
        return EmpCode;
    }

    public void setEmpCode(String empCode) {
        EmpCode = empCode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRollno() {
        return Rollno;
    }

    public void setRollno(String rollno) {
        Rollno = rollno;
    }
}
