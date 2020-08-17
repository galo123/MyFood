package com.example.myfood.Class;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Family implements Serializable {

    //family contains group of 10 users maximum
    private final int MAX_USERS = 10;

    private  String groupName;
    private int groupCode;
    private Date creationTime;
    private int score;
    private ArrayList<User> familyMembers;

    public Family(String groupName, ArrayList<User> familyMembers) {
        this.groupName = groupName;
        this.groupCode = this.hashCode();
        this.creationTime =  Calendar.getInstance().getTime();
        this.score = 0;
        this.familyMembers = familyMembers; // include group creator
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

}