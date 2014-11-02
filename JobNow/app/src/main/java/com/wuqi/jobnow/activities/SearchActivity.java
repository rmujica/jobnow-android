package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.adapters.OffersAdapter;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchActivity extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    Location location;
    LocationClient locationClient;
    List<Offer> myOffers = new ArrayList<Offer>();
    private OffersAdapter adapter;
    String keyword;

    @InjectView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Action Bar Changes
        setTitle("");
        getActionBar().setIcon(R.drawable.logo);

        ButterKnife.inject(this);

        adapter = new OffersAdapter(getFragmentManager());
        locationClient = new LocationClient(this, this, this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    //Funcion que corre con cada input de busqueda
    public void doMySearch(String query){
        keyword = query;
        TextView text = (TextView) findViewById(R.id.textView2);
        ImageView arrow_image = (ImageView) findViewById(R.id.imageArrow);
        text.setVisibility(View.GONE);
        arrow_image.setVisibility(View.GONE);
        progress.setVisibility((View.VISIBLE));
        locationClient.connect();
    }

    private void populateListView(){
        ArrayAdapter<Offer> adapter =   new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.searchListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Offer>{

        public MyListAdapter(){
            super( SearchActivity.this, R.layout.item_search_view, myOffers);
            Offer d = myOffers.get(0);
            System.out.println("ASD " + d.short_description);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Make sure we have a view to work with( may have been given null)
            System.out.println("GETVIEWWWWWWWWWWWWWWWWW");
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_search_view,parent,false);
            }

            Offer currentOffer = myOffers.get(position);

            //Find the job to work with
            TextView short_description = (TextView) itemView.findViewById(R.id.item_textView);


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

                return itemView;
            }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.clearList();
        locationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // get last fix
        location = locationClient.getLastLocation();

        JobnowApplication.getInstance().getApi().getOffersByKeywords(keyword, new Callback<OfferSearchResult>() {
            @Override
            public void success(OfferSearchResult offerSearchResult, Response response) {
                List<Offer> result = offerSearchResult.result;
                adapter.addOffers(result);
                System.out.println("TAMAÑO ADAPTER: "+ adapter.getTotal());
                Log.d("com.wuqi.jobnow", "loaded offers 2");
                modifyOffersList(adapter.getTotal());
                if(adapter.getTotal() != 0)
                    populateListView();
                changeLayout();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("com.wuqi.jobnow", "retrofit error: " +error.getMessage());
            }
        });
    }

    public void changeLayout() {

        TextView text = (TextView) findViewById(R.id.textView2);
        ImageView arrow_image = (ImageView) findViewById(R.id.imageArrow);
        TextView text_no_search = (TextView) findViewById(R.id.textViewNoSearch);
        ImageView image_no_search = (ImageView) findViewById(R.id.imageNoSearch);
        ListView search = (ListView) findViewById(R.id.searchListView);

        if( adapter.getTotal() != 0) {
            progress.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);

        }else if(adapter.getTotal() == 0){
            text_no_search.setVisibility(View.VISIBLE);
            image_no_search.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }

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
        adapter.clearList();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        // Get the SearchView and set the searchable configuration


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
}
