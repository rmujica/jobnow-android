package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.adapters.OffersAdapter;
import com.wuqi.jobnow.entities.Constants;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyListActivity extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    Location location;
    LocationClient locationClient;

    List<Offer> myOffers = new ArrayList<Offer>();


    private OffersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        // Action Bar Changes
        setTitle("");
        getActionBar().setIcon(R.drawable.logo);

        adapter = new OffersAdapter(getFragmentManager());
        locationClient = new LocationClient(this, this, this);

        registerClickCallback();

    }

    private void populateListView(){
        ArrayAdapter<Offer> adapter =   new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.myJobsListView);
        list.setAdapter(adapter);
        System.out.println("HOLAAAAAAAAAAAAAAAAA");

    }
    private void registerClickCallback(){
        ListView list = (ListView) findViewById(R.id.myJobsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View viewClicked,
                                    int position, long id){

                Offer offer = myOffers.get(position);
                Intent intent = new Intent(MyListActivity.this, DetailActivity.class);
                intent.putExtra("short_description", offer.short_description);
                intent.putExtra("price", offer.price);
                intent.putExtra("long_description", offer.long_description);
                intent.putExtra("lat", offer.lat);
                intent.putExtra("lng", offer.lng);
                startActivity(intent);

            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Offer>{

        public MyListAdapter(){
            super( MyListActivity.this, R.layout.item_view, myOffers);
            Offer d = myOffers.get(0);
            System.out.println(d.short_description);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure we have a view to work with( may have been given null)
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }

            Offer currentOffer = myOffers.get(position);

            //Find the job to work with
            TextView short_description = (TextView) itemView.findViewById(R.id.item_textView);
            TextView status = (TextView) itemView.findViewById(R.id.item_status);

            //Change short description size
            String shorter_description;
            if( currentOffer.short_description.length() >= 25 )
                shorter_description = currentOffer.short_description.substring(0,25) + "...";
            else
                shorter_description = currentOffer.short_description;
            short_description.setText(shorter_description);

            //Change imageView
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_imageView);

            switch(Integer.parseInt(currentOffer.category)) {
                case 1:
                    imageView.setImageResource(R.drawable.categoria1a);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.categoria2a);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.categoria3a);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.categoria4a);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.categoria5a);
                    break;
            }

            switch(Integer.parseInt(currentOffer.state)) {
                case 1:
                    status.setText("Pendiente");
                    status.setTextColor(Color.BLACK);
                    break;
                case 2:
                    status.setText("Aceptado");
                    status.setTextColor(Color.GREEN);
                    break;
                case 3:
                    status.setText("Rechazado");
                    status.setTextColor(Color.RED);
                    break;

            }

            return itemView;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
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
    public void onConnected(Bundle bundle) {
        location = locationClient.getLastLocation();

        SharedPreferences sharedPref =
                getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);

        final String id = sharedPref.getString(Constants.USER_ID,"nulleitor");

        location = locationClient.getLastLocation();

        myOffers.clear();
        // get offers by location
        String latlng = "%f,%f";
        latlng = String.format(latlng, location.getLatitude(), location.getLongitude());
        JobnowApplication.getInstance().getApi().getOffers( latlng ,new Callback<OfferSearchResult>() {

            @Override
            public void success(OfferSearchResult offerSearchResult, Response response) {
                List<Offer> result = offerSearchResult.result;
                adapter.addFilterOffers(result, id);
                Log.d("com.wuqi.jobnow", "loaded offers");
                modifyOffersList(adapter.getTotal());
                populateListView();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("com.wuqi.jobnow", "retrofit error: " + error.getMessage());
            }
        });
    }

    public void modifyOffersList(int size) {
        int position = 0;

        while(position != (size)) {
            myOffers.add(adapter.getOffer(position));
            position = position + 1;
        }

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}

