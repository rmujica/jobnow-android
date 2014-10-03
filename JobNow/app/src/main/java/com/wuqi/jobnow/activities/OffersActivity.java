package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.adapters.OffersAdapter;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OffersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        getActionBar().setIcon(R.drawable.jobnowlogo);
        getActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Ofertas </font>"));

        // load offers
        final OffersAdapter adapter = new OffersAdapter(this);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(adapter);


        JobnowApplication.getInstance().getApi().getOffers(new Callback<OfferSearchResult>() {
            @Override
            public void success(OfferSearchResult offerSearchResult, Response response) {
                List<Offer> result = offerSearchResult.result;
                adapter.addOffers(result);
                Toast.makeText(OffersActivity.this, "Loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
