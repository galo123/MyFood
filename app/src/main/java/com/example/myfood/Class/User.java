package com.example.myfood.Class;

import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private int groupCode; //user can be member of one group only

    public User(String email, String password, String firstName, String lastName, Date birthDay) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
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
