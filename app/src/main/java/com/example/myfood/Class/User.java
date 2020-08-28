package com.example.myfood.Class;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDay;


    private int groupCode; //user can be member of one group only
    private boolean enablePics;

    //constructor if the user enters group code
    public User(String email, String firstName, String lastName, String password, String birthDay) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.enablePics = true;
            }
    //constructor if the user doesn't enter group code?


    //default for firebase
    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }


    public int getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(int groupCode) {
        this.groupCode = groupCode;
    }

    void joinNewGroup(String groupName, ArrayList<User> users) {
        Family newGroup = new Family(groupName,users);
        this.groupCode = newGroup.getGroupCode();
        //catch errors
    }

    void joinExistingGroup(int groupCode) {
        //check if the code is valid - db
        this.groupCode = groupCode;
        //add the new user in db
    }
}
