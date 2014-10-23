package com.wuqi.jobnow.api;

import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;
import com.wuqi.jobnow.entities.User;
import com.wuqi.jobnow.entities.UserSearchResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface JobnowApi {
    @FormUrlEncoded
    @POST("/users/login")
    void loginUser(@Field("email") String email,
                   @Field("password") String password,
                   Callback<User> cb);

    @FormUrlEncoded
    @POST("/offers/{offer}/applications")
    void applyToJob(@Path("offer") String offer_id,
                    @Field("user_id") String user_id,
                    Callback<Offer> cb);

    @GET("/offers")
    void getOffers(@Query("l") String latlng, Callback<OfferSearchResult> cb);

    @GET("/users")
    void getUsers(@Query("l") String latlng, Callback<UserSearchResult> cb);

    @GET("/offers")
    void getFilterOffers(@Query("l") String id, Callback<OfferSearchResult> cb);
}
