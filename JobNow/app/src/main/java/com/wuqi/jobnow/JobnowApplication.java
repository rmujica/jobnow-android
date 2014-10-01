package com.wuqi.jobnow;

import android.app.Application;

import com.wuqi.jobnow.api.JobnowApi;

import retrofit.RestAdapter;

public class JobnowApplication extends Application {
    private static JobnowApplication instance;
    private JobnowApi api;

    public JobnowApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://jobnow.herokuapp.com/v2")
                .build();

        api = restAdapter.create(JobnowApi.class);
    }

    public JobnowApi getApi() {
        return api;
    }

    public static JobnowApplication getInstance() {
        return instance;
    }
}