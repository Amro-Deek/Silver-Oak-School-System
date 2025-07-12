package com.example.finalproject.models;

public class AssignmentResponse {
    private final String studentName;
    private final String submissionDate;
    private final String attachmentUrl;
    private final String message;

    public AssignmentResponse(String studentName, String submissionDate, String attachmentUrl, String message) {
        this.studentName = studentName;
        this.submissionDate = submissionDate;
        this.attachmentUrl = attachmentUrl;
        this.message = message;
    }

    public String getStudentName() { return studentName; }
    public String getSubmissionDate() { return submissionDate; }
    public String getAttachmentUrl() { return attachmentUrl; }
    public String getMessage() { return message; }

}
