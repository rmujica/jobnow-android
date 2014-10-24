package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.adapters.OffersAdapter;
import com.wuqi.jobnow.adapters.UsersAdapter;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;
import com.wuqi.jobnow.entities.User;
import com.wuqi.jobnow.entities.UserSearchResult;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyProfileActivity extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    Location location;
    LocationClient locationClient;

    List<User> myUsers = new ArrayList<User>();

    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        adapter = new UsersAdapter();
        locationClient = new LocationClient(this, this, this);
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

        //ID DE IGNACIO ESPINOZA
        final String id = "5437f39d7a248b0002648ad3";
        location = locationClient.getLastLocation();

        JobnowApplication.getInstance().getApi().getUsers(id, new Callback<User>() {

            @Override
            public void success(User user, Response response) {
                System.out.println("EXITO!!");
                //IMPRIME NULO
                System.out.println(user.email);
                //adapter.addUser(result);
                Log.d("com.wuqi.jobnow", "loaded users");

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
