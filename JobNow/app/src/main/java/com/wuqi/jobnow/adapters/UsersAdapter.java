package com.wuqi.jobnow.adapters;


import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.User;

import java.util.LinkedList;
import java.util.List;

public class UsersAdapter {

    private List<User> user = new LinkedList<User>();
    private String login_id;

    public void addUser(List<User> users){
        this.user.addAll(users);
    }

    public void setLogin_id(String login_id){ this.login_id = login_id;}

    public String getLogin_id(){return this.login_id;}

    public User getUser(){
        return user.get(0);
    }

}
