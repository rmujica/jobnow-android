package com.wuqi.jobnow.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.entities.Constants;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.entities.OfferSearchResult;
import com.wuqi.jobnow.fragments.OfferCardFragment;

import java.util.LinkedList;
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

    LinkedList<Offer> offers = new LinkedList<Offer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.inject(this);

        getActionBar().setIcon(R.drawable.jobnowlogo);
        getActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Ofertas</font>"));

        // load offers

        JobnowApplication.getInstance().getApi().getOffers(new Callback<OfferSearchResult>() {
            @Override
            public void success(OfferSearchResult offerSearchResult, Response response) {
                List<Offer> result = offerSearchResult.result;
                offers.addAll(result);
                final OffersAdapter adapter = new OffersAdapter(getFragmentManager());
                pager.setAdapter(adapter);
                adapter.setCount(offers.size());
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

    private class OffersAdapter extends FragmentStatePagerAdapter {

        Integer fragmentCount = 0;

        public void setCount(Integer n) {
            fragmentCount = n;
            notifyDataSetChanged();
        }

        public OffersAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return OfferCardFragment.newInstance(offers.get(position));
        }

        @Override
        public int getCount() {
            return fragmentCount;
        }
    }
}
