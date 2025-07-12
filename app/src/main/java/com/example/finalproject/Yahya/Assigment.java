package com.example.finalproject.Yahya;

public class Assigment {
    private String id;
    private String title;
    private String subject;
    private String duoDate;
    private String status;
    private String description;
    private String attachmentUrl;

    // Update constructor
    public Assigment(String id, String title, String subject, String duoDate, String status, String description, String attachmentUrl) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.duoDate = duoDate;
        this.status = status;
        this.description = description;
        this.attachmentUrl = attachmentUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDuoDate() {
        return duoDate;
    }

    public void setDuoDate(String duoDate) {
        this.duoDate = duoDate;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}