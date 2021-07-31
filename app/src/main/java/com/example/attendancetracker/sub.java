package com.example.attendancetracker;

public class sub {

    String subject;
    String subject_code;

    public sub(String subject, String subject_code) {
        this.subject = subject;
        this.subject_code = subject_code;
    }

    public static Object getText() {
        return null;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }
}
