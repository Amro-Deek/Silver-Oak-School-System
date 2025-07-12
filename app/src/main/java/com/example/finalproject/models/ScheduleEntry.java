package com.example.finalproject.models;

public class ScheduleEntry {
    private String subjectName;
    private String room;
    private String day;
    private String startTime;
    private String endTime;

    public ScheduleEntry(String subjectName, String room, String day, String startTime, String endTime) {
        this.subjectName = subjectName;
        this.room = room;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSubjectName() { return subjectName; }
    public String getRoom() { return room; }
    public String getDay() { return day; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
}

