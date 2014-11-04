package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.data.OffersLayoutManager;
import com.wuqi.jobnow.data.RecyclingOffersAdapter;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OffersActivity extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    @InjectView(R.id.list)
    RecyclerView list;

    @InjectView(R.id.progress)
    ProgressBar progress;

    Location location;
    LocationClient locationClient;
    RecyclingOffersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.inject(this);

        // setup recyclerview
        final LinearLayoutManager layoutManager = new OffersLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list.setLayoutManager(layoutManager);
        adapter = new RecyclingOffersAdapter(this);
        list.setAdapter(adapter);

        // setup location client
        locationClient = new LocationClient(this, this, this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.offers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);

        Intent i = null;

        switch(item.getItemId()){
            case R.id.mylist:
                i = new Intent(this, MyListActivity.class);
                break;
            case R.id.myProfile:
                i = new Intent(this, MyProfileActivity.class);
                break;
        }

        if (i != null) startActivity(i);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        // get last fix and get new offers only if we moved 50 meters
        Location newLocation = locationClient.getLastLocation();
        if (location != null && location.distanceTo(newLocation) < 50) {
            location = newLocation;
            return;
        }

        // get offers by location
        location = newLocation;
        String loc = String.format("%f,%f", location.getLatitude(), location.getLongitude());
        JobnowApplication.getInstance().getApi().getOffers(loc, new Callback<OfferSearchResult>() {
            @Override
            public void success(OfferSearchResult offerSearchResult, Response response) {
                List<Offer> result = offerSearchResult.result;
                adapter.addOffers(result);
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("com.wuqi.jobnow", "retrofit error: " +error.getMessage());
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
