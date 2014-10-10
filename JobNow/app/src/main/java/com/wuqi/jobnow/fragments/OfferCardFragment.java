package com.wuqi.jobnow.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.activities.DetailActivity;
import com.wuqi.jobnow.entities.Constants;
import com.wuqi.jobnow.entities.Offer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OfferCardFragment extends Fragment {

    private Offer offer;

    @InjectView(R.id.price)
    TextView price;

    @InjectView(R.id.short_description)
    TextView short_description;

    @InjectView(R.id.icon_image)
    ImageView icon;

    @OnClick(R.id.ok)
    public void ok(final ImageView view) {
        // are we logged in?
        SharedPreferences sharedPref =
                getActivity().getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedPref.getString(Constants.USER_ID, "");

        if (user_id.isEmpty()) return;

        JobnowApplication.getInstance().getApi().applyToJob(offer.id, user_id, new Callback<Offer>() {
            @Override
            public void success(Offer offer, Response response) {
                //

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @OnClick(R.id.info)
    public void getMoreInfo(ImageView view) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("short_description", offer.short_description);
        intent.putExtra("price", offer.price);
        intent.putExtra("long_description", offer.long_description);
        intent.putExtra("lat", offer.lat);
        intent.putExtra("lng", offer.lng);
        startActivity(intent);
    }

    public static OfferCardFragment newInstance(Offer offer) {
        OfferCardFragment fragment = new OfferCardFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.OFFER_EXTRA, offer);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        offer = getArguments().getParcelable(Constants.OFFER_EXTRA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.detail_offer, container, false);
        ButterKnife.inject(this, rootView);

        if (offer != null) {
            price.setText("$" + offer.price);
            short_description.setText(offer.short_description);

            //Change Text size according to short_description length
            if( short_description.length() >= 30){
                short_description.setTextSize(30);
            }

            //Change Image according to category
            if(offer.category.equals("1")){
                icon.setImageResource(R.drawable.categoria1a);
            }
            if(offer.category.equals("2")){
                icon.setImageResource(R.drawable.categoria2a);
            }
            if(offer.category.equals("3")){
                icon.setImageResource(R.drawable.categoria3a);
            }
            if(offer.category.equals("4")){
                icon.setImageResource(R.drawable.categoria4a);
            }
            if(offer.category.equals("5")){
                icon.setImageResource(R.drawable.categoria5a);
            }
        } else {
            Log.d("com.wuqi.jobnow", "offer is null in offersadapter");
        }



        return rootView;
    }
}
