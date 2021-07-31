package com.example.attendancetracker.admin.StudentAdmin;

public class asdisplay {

    private String Name,UID;

    public asdisplay(){

    }

    public asdisplay(String name, String UID) {
        this.Name = name;
        this.UID = UID;
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
}