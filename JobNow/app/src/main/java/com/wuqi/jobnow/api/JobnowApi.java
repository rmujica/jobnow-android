package com.wuqi.jobnow.api;

import com.wuqi.jobnow.entities.User;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface JobnowApi {
    @FormUrlEncoded
    @POST("/users/login")
    void loginUser(@Field("email") String email,
                   @Field("password") String password,
                   Callback<User> cb);
}
