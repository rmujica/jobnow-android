package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wuqi.jobnow.R;

public class DetailActivity extends Activity {

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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
