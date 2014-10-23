package com.wuqi.jobnow.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("_id") public String id;
    public String type;
    public String email;
    public String password;
    public String first_name;
    public String last_name;
    public String birth_date;
    public String location;
    public String occupation;
    public String facebook_user;
    public List<String> applications;
}
