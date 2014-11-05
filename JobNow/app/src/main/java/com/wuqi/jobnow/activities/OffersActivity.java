package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.data.OffersLayoutManager;
import com.wuqi.jobnow.data.RecyclingOffersAdapter;
import com.wuqi.jobnow.data.SearchResultAdapter;
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

    private static final int TRIGGER_SEARCH = 0x1;
    private final long SEARCH_TRIGGER_DELAY_IN_MS = 1000;

    @InjectView(R.id.list)
    RecyclerView list;

    @InjectView(R.id.progress)
    ProgressBar progress;

    Location location;
    LocationClient locationClient;
    RecyclingOffersAdapter adapter;

    Handler queryHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TRIGGER_SEARCH) {
                Log.d("com.wuqi.jobnow", "msg rcv, doing search: " + msg.obj);
                JobnowApplication.getInstance().getApi().getOffersByKeywords((String) msg.obj,
                        new retrofit.Callback<OfferSearchResult>() {
                    @Override
                    public void success(OfferSearchResult offerSearchResult, Response response) {
                        Log.d("com.wuqi.jobnow", "search done, displaying");
                        // show result in query box
                        String[] columns = new String[] {"_ID", "SUGGEST_COLUMN_TEXT_1"};
                        Object[] temp = new Object[] {0, "default"};
                        int i = 0;

                        MatrixCursor cursor = new MatrixCursor(columns);

                        for (Offer o : offerSearchResult.result) {
                            temp[0] = i;
                            temp[1] = o.short_description;
                            Log.d("com.wuqi.jobnow", "search result: " + o.short_description);
                            cursor.addRow(temp);
                            i++;
                        }

                        String[] from = {"SUGGEST_COLUMN_TEXT_1"};
                        int[] to = {R.id.title};

                        SearchManager manager =
                                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
                        /*searchView.setSuggestionsAdapter(
                                new SearchResultAdapter(
                                        OffersActivity.this, cursor, offerSearchResult.result));*/
                        searchView.setSuggestionsAdapter(
                                new SimpleCursorAdapter(
                                        OffersActivity.this, R.layout.detail_offer_slim, cursor, from, to, 0));
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        }
    };
    private SearchView searchView;

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

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) {
                    Message msg = new Message();
                    msg.what = TRIGGER_SEARCH;
                    msg.obj = s;
                    queryHandler.removeMessages(TRIGGER_SEARCH);
                    queryHandler.sendMessageDelayed(msg, SEARCH_TRIGGER_DELAY_IN_MS);
                }
                return true;
            }
        });

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
