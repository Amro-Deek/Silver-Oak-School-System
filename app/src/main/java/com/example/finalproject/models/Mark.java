package com.example.finalproject.models;

public class Mark {
    private int id;
    private String examType;
    private double mark;
    public Mark(int id, String examType, double mark) {
        this.id = id; this.examType = examType; this.mark = mark;
    }
    public int getId() { return id; }
    public String getExamType() { return examType; }
    public double getMark() { return mark; }
}