package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.wuqi.jobnow.R;

public class DetailActivity extends Activity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("");
        getActionBar().setIcon(R.drawable.logo);


        Intent myIntent = getIntent();
        String price = myIntent.getStringExtra("price");
        String shortdescription = myIntent.getStringExtra("short_description");
        String longdescription = myIntent.getStringExtra("long_description");
        Double lat = myIntent.getDoubleExtra("lat", 0.0);
        Double lng = myIntent.getDoubleExtra("lng", 0.0);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        MapsInitializer.initialize(this);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14);
        map.animateCamera(update);

        TextView text_price = (TextView)findViewById(R.id.price);
        TextView short_description = (TextView)findViewById(R.id.short_description);
        TextView long_description = (TextView)findViewById(R.id.long_description);

        short_description.setText(shortdescription);
        text_price.setText("$" + price);
        long_description.setText(longdescription);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
