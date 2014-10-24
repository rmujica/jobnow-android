package com.wuqi.jobnow.adapters;


import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.User;

import java.util.LinkedList;
import java.util.List;

public class UsersAdapter {

    private List<User> user = new LinkedList<User>();


    public void addUser(List<User> users){
        this.user.addAll(users);
    }

    public User getUser(){
        return user.get(0);
    }

}
