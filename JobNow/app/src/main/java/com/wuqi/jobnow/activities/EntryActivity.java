package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.wuqi.jobnow.entities.Constants;

public class EntryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // are we logged in?
        SharedPreferences sharedPref =
                getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        Boolean isLogged = sharedPref.getBoolean(Constants.LOGGED_IN, false);

        if (!isLogged) {
            // load login activity
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // load offers activity
            Intent intent = new Intent(this, OffersActivity.class);
            System.out.println("EL ID ES : " + sharedPref.getString(Constants.USER_ID,"nulleitor"));
            startActivity(intent);
            finish();
        }
    }

}
