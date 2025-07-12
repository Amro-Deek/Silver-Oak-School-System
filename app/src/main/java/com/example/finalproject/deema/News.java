package com.example.finalproject.deema;


import java.util.ArrayList;

public class News {

    int imageid;
    String newsinfo;
    String title;
    private ArrayList<News> newsliz;


    public News() {
    }

    public News(int imageid, String newsinfo, String title) {
        this.imageid = imageid;
        this.newsinfo = newsinfo;
        this.title = title;
    }

    public ArrayList<News> getNewsliz() {
        return newsliz;
    }

    public void setNewsliz(ArrayList<News> newsliz) {
        this.newsliz = newsliz;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getNewsinfo() {
        return newsinfo;
    }

    public void setNewsinfo(String newsinfo) {
        this.newsinfo = newsinfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "News{" +
                "imageid=" + imageid +
                ", newsinfo='" + newsinfo + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
