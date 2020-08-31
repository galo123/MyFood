package com.example.myfood.Class;

import java.io.Serializable;
import java.util.Objects;

public class FoodItem  implements Serializable {
    private String foodDiscription;
    private int amount;
    private String unit;
    private String url;
    private  User user;
    private  Group group;
    private int id;


    public FoodItem(String foodDiscription, int amount, String unit, String url, User user, Group group, int id){
        this.foodDiscription=foodDiscription;
        this.amount=amount;
        this.unit=unit;
        this.url=url;
        this.user = user;
        this.group = group;
        this.id = id;
    }
    public FoodItem(String foodDiscription, int amount,String unit, User user, Group group, int id){
        this.foodDiscription=foodDiscription;
        this.amount=amount;
        this.unit=unit;
        this.user = user;
        this.group = group;
        this.id = id;

    }


    public FoodItem(){

    }

    public String getFoodDiscription() {
        return foodDiscription;
    }

    public int getAmount() {
        return amount;
    }
    public String getUnit() {
        return unit;
    }
    public String getUrl() {
        return url;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public User getUser(){return user;}

    public void setUser(User user){this.user = user;}

    public Group getGroup(){return group;}

    public void setGroup(Group group){this.group = group;}

    public int getId(){return this.id;}

    public void setId(int id){this.id = id;}

    public void setFoodDiscription(String foodDiscription) {
        this.foodDiscription = foodDiscription;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodItem foodItem = (FoodItem) o;
        return Objects.equals(foodDiscription, foodItem.foodDiscription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodDiscription);
    }
}


