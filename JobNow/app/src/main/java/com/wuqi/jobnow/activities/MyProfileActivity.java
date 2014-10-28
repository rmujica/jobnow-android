package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.adapters.OffersAdapter;
import com.wuqi.jobnow.adapters.UsersAdapter;
import com.wuqi.jobnow.entities.Constants;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;
import com.wuqi.jobnow.entities.User;
import com.wuqi.jobnow.entities.UserSearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyProfileActivity extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    Location location;
    LocationClient locationClient;

    List<User> myUsers = new ArrayList<User>();

    @InjectView(R.id.progress)
    ProgressBar progress;

    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.inject(this);
        // Action Bar Changes
        setTitle("");
        getActionBar().setIcon(R.drawable.logo);

        adapter = new UsersAdapter();
        locationClient = new LocationClient(this, this, this);
    }

    public void modifyLayout(User user){
        TextView full_name = (TextView) findViewById(R.id.full_name);
        TextView mail = (TextView) findViewById(R.id.email);
        ImageView profile_image = (ImageView) findViewById(R.id.imageView);

        full_name.setText(user.first_name + " " + user.last_name );
        mail.setText(user.email );
        progress.setVisibility(View.INVISIBLE);
        full_name.setVisibility(View.VISIBLE);
        mail.setVisibility(View.VISIBLE);
        profile_image.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        locationClient.connect();
    }

    @Override
    public void onStop() {
        locationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        location = locationClient.getLastLocation();

        //GET LOGIN ID
        SharedPreferences sharedPref =
                getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);

        final String id = sharedPref.getString(Constants.USER_ID,"nulleitor");
        location = locationClient.getLastLocation();

        JobnowApplication.getInstance().getApi().getUsers(id, new Callback<User>() {

            @Override
            public void success(User user, Response response) {
                System.out.println("EXITO!!");
                Log.d("com.wuqi.jobnow", "loaded users");
                modifyLayout(user);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("com.wuqi.jobnow", "retrofit error: " + error.getMessage());
            }
        });
    }



    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
