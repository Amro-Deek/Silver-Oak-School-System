package com.example.finalproject.models;

public class Assignment {
    private int id, subjectId, teacherId;
    private String title, description, dueDate, attachmentUrl, subjectName;

    public Assignment(int id, int subjectId, int teacherId, String title, String description, String dueDate, String attachmentUrl, String subjectName) {
        this.id = id;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.attachmentUrl = attachmentUrl;
        this.subjectName = subjectName;
    }

    public int getId() { return id; }
    public int getSubjectId() { return subjectId; }
    public int getTeacherId() { return teacherId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public String getAttachmentUrl() { return attachmentUrl; }
    public String getSubjectName() { return subjectName; }
}

