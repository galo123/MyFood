package com.example.myfood.Class;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Group implements Serializable {

    //family contains group of 10 users max
    private final int MAX_USERS = 10;

    private  String groupName;
    private int groupCode;
    private Date creationTime;
    private int score;
    private ArrayList<User> familyMembers;
    private ArrayList<Integer> picturesIds;

    public Group(String groupName) {
        this.groupName = groupName;
        this.groupCode = this.hashCode();
        this.creationTime =  Calendar.getInstance().getTime();
        this.score = 0;
        this.familyMembers = new ArrayList<User>();
        this.picturesIds = new ArrayList<Integer>();
     //   this.familyMembers = familyMembers; // include group creator
    }

    public Group() {

    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
       //group name doesnt need to be unique value
        this.groupName = groupName;
    }

    public int getGroupCode() {
        return groupCode;
    }

    public Date getCreationTime() {
        return creationTime;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<User> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(ArrayList<User> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public void addFamilyMemberToGroup(User newMember) {
        this.familyMembers.add(newMember);
    }

    public ArrayList<Integer> getPictures(){return picturesIds;}

    public void setPictures(ArrayList<Integer> pictures){
        this.picturesIds = pictures;
    }

    public void addPictureId(Integer pictureId){
        if(this.picturesIds != null) {
            this.picturesIds.add(pictureId);
        }
        else{
            this.picturesIds = new ArrayList<Integer>();
            this.picturesIds.add(pictureId);
        }
    }

}
