package com.example.finalproject.Yahya;

public class ScheduleItem {
    private  String day;
    private  String subjectName;
    private  String teacherName;
    private  String startTime;
    private String endTime;
    private  String location;

    public ScheduleItem() {
    }

    public ScheduleItem(String subjectName, String day, String teacherName, String startTime, String endTime, String location) {
        this.subjectName = subjectName;
        this.day = day;
        this.teacherName = teacherName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
