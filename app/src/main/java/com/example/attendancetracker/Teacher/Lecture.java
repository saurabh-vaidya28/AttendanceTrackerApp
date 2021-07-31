package com.example.attendancetracker.Teacher;

public class Lecture {
private String teacherID,teachersName,SubjectName,SubjectCode,Date,Time,SubCodeName,Department,Year,Semester;
private String lectureNo;
    public Lecture() {
    }

//    public Lecture(String teacherID, String teachersName, String subjectName, String subjectCode, String date, String time, String subCodeName, String department, String year, String semester) {
//        this.teacherID = teacherID;
//        this.teachersName = teachersName;
//        SubjectName = subjectName;
//        SubjectCode = subjectCode;
//        Date = date;
//        Time = time;
//        SubCodeName = subCodeName;
//        Department = department;
//        Year = year;
//        Semester = semester;
//    }

    public Lecture(String teacherID, String teachersName, String subjectName, String subjectCode, String date, String time, String subCodeName, String department, String year, String semester, String lectureNo) {
        this.teacherID = teacherID;
        this.teachersName = teachersName;
        SubjectName = subjectName;
        SubjectCode = subjectCode;
        Date = date;
        Time = time;
        SubCodeName = subCodeName;
        Department = department;
        Year = year;
        Semester = semester;
        this.lectureNo = lectureNo;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "teacherID='" + teacherID + '\'' +
                ", teachersName='" + teachersName + '\'' +
                ", SubjectName='" + SubjectName + '\'' +
                ", SubjectCode='" + SubjectCode + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                ", SubCodeName='" + SubCodeName + '\'' +
                ", Department='" + Department + '\'' +
                ", Year='" + Year + '\'' +
                ", Semester='" + Semester + '\'' +
                ", lectureNo='" + lectureNo + '\'' +
                '}';
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
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


    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeachersName() {
        return teachersName;
    }

    public void setTeachersName(String teachersName) {
        this.teachersName = teachersName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        SubjectCode = subjectCode;
    }

    public String getLectureNo() {
        return lectureNo;
    }

    public void setLectureNo(String lectureNo) {
        this.lectureNo = lectureNo;
    }
}
