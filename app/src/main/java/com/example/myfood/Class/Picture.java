package com.example.myfood.Class;

import android.graphics.Bitmap;

import java.util.Date;

public class Picture {
    private String imageEncoded;
    private String date;
    private  User user;


    public Picture(){}

    public Picture(String imageEncoded, String date, User user){
        this.imageEncoded = imageEncoded;
        this.date = date;
        this.user = user;
    }

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}

    public User getUser(){return user;}

    public void setUser(User user){this.user = user;}

    public String getImageEncoded(){ return imageEncoded;}

    public void setImageEncoded(String imageEncoded){this.imageEncoded = imageEncoded;}
}
