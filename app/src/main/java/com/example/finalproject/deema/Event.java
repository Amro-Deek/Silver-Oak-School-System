package com.example.finalproject.deema;


public class Event {
    private String dayOfWeek;
    private String day;
    private String time;
    private String title;
    private String description;
    private int imageResource;

    // Full constructor
    public Event(String day, String dayOfWeek, String time, String title, String description, int imageResource) {
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }

    public Event() {
    }

    // Getters and setters
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Event{" +
                "day='" + day + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }


}
