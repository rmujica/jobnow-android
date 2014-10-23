package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
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
import com.wuqi.jobnow.adapters.OffersAdapter;
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

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.progress)
    ProgressBar progress;

    Location location;
    LocationClient locationClient;
    private OffersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.inject(this);

        getActionBar().setIcon(R.drawable.jobnowlogo);
        getActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Ofertas</font>"));

        // load offers
        adapter = new OffersAdapter(getFragmentManager());
        pager.setAdapter(adapter);

        locationClient = new LocationClient(this, this, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        locationClient.connect();
    }

    @Override
    public void onStop() {
        adapter.clearList();
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
        switch(item.getItemId()){
            case R.id.mylist:
                goToListLayout();
            break;
            case R.id.myProfile:
                goToMyProfile();
                break;
        }
        return true;
    }

    private void goToListLayout(){
        Intent i = new Intent(this, MyListActivity.class);
        startActivity(i);
    }
    private void goToMyProfile(){
        Intent i = new Intent(this, MyProfileActivity.class);
        startActivity(i);
    }


    @Override
    public void onConnected(Bundle bundle) {
        // get last fix
        location = locationClient.getLastLocation();

        // get offers by location
        String latlng = "%f,%f";
        latlng = String.format(latlng, location.getLatitude(), location.getLongitude());
        JobnowApplication.getInstance().getApi().getOffers(latlng, new Callback<OfferSearchResult>() {
            @Override
            public void success(OfferSearchResult offerSearchResult, Response response) {
                List<Offer> result = offerSearchResult.result;
                adapter.addOffers(result);
                progress.setVisibility(View.INVISIBLE);
                Log.d("com.wuqi.jobnow", "loaded offers");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("com.wuqi.jobnow", "retrofit error: " +error.getMessage());
            }
        });
    }

    @Override
    public void onDisconnected() {
        adapter.clearList();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
