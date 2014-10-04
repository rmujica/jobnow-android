package com.wuqi.jobnow.entities;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id") public String id;
    public String type;
    public String email;
    public String password;
}
