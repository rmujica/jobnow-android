package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

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

public class OffersActivity extends Activity {

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.inject(this);

        getActionBar().setIcon(R.drawable.jobnowlogo);
        getActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Ofertas</font>"));

        // load offers
        final OffersAdapter adapter = new OffersAdapter(this, pager);
        pager.setAdapter(adapter);

        JobnowApplication.getInstance().getApi().getOffers(new Callback<OfferSearchResult>() {
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
        return super.onOptionsItemSelected(item);
    }
}
