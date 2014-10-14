package com.wuqi.jobnow.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuqi.jobnow.R;
import com.wuqi.jobnow.activities.DetailActivity;
import com.wuqi.jobnow.entities.Constants;
import com.wuqi.jobnow.entities.Offer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OfferFragment extends Fragment {

    Offer offer;

    @InjectView(R.id.price)
    TextView price;

    @InjectView(R.id.short_description)
    TextView shortDescription;

    @InjectView(R.id.icon_image)
    ImageView icon;

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

    public static OfferFragment newInstance(Offer offer) {
        // this is the correct way to instance a new offerfragment
        OfferFragment f = new OfferFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.OFFER_EXTRA, offer);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offer = getArguments().getParcelable(Constants.OFFER_EXTRA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.detail_offer, container, false);
        ButterKnife.inject(this, rootView);

        if (offer != null) {
            price.setText("$" + offer.price);
            shortDescription.setText(offer.short_description);

            //Change Text size according to short_description length
            if( shortDescription.length() >= 30){
                shortDescription.setTextSize(30);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
