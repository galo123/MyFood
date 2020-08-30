package com.example.myfood.Class;

import android.graphics.Bitmap;

import java.util.Date;

public class Picture {
    private String imageEncoded;
    private String date;
    private  User user;
    private  Group group;
    private int id;


    public Picture(){}

    public Picture(String imageEncoded, String date, User user, Group group, int id){
        this.imageEncoded = imageEncoded;
        this.date = date;
        this.user = user;
        this.group = group;
        this.id = id;
    }

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}

    public User getUser(){return user;}

    public void setUser(User user){this.user = user;}

    public String getImageEncoded(){ return imageEncoded;}

    public void setImageEncoded(String imageEncoded){this.imageEncoded = imageEncoded;}

    public Group getGroup(){return group;}

    public void setGroup(Group group){this.group = group;}

    public int getId(){return this.id;}

    public void setId(int id){this.id = id;}
}
