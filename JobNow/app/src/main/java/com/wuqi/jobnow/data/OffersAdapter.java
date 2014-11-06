package com.wuqi.jobnow.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuqi.jobnow.R;
import com.wuqi.jobnow.activities.DetailActivity;
import com.wuqi.jobnow.entities.Offer;

import java.util.ArrayList;
import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OfferViewHolder> {

    List<Offer> offers = new ArrayList<Offer>(100);
    Context context;
    boolean isSlim;

    public OffersAdapter(Context context) {
        this.context = context;
    }

    public OffersAdapter(Context context, boolean isSlim) {
        this.context = context;
        this.isSlim = isSlim;
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view;
        if (!isSlim) {
            view = LayoutInflater.from(viewHolder.getContext()).inflate(R.layout.detail_offer, viewHolder, false);
        } else {
            view = LayoutInflater.from(viewHolder.getContext()).inflate(R.layout.detail_offer_slim, viewHolder, false);
        }
        return new OfferViewHolder(view, isSlim);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder ovh, final int position) {
        final Offer offer = offers.get(position);

        if (offer != null) {
            ovh.price.setText("$" + offer.price);
            ovh.short_description.setText(offer.short_description);

            //Change Image according to category
            if (offer.category.equals("1")) {
                ovh.icon.setImageResource(R.drawable.categoria1a);
            }

            if (offer.category.equals("2")) {
                ovh.icon.setImageResource(R.drawable.categoria2a);
            }

            if (offer.category.equals("3")) {
                ovh.icon.setImageResource(R.drawable.categoria3a);
            }

            if (offer.category.equals("4")) {
                ovh.icon.setImageResource(R.drawable.categoria4a);
            }

            if (offer.category.equals("5")) {
                ovh.icon.setImageResource(R.drawable.categoria5a);
            }

            ovh.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("short_description", offer.short_description);
                    intent.putExtra("price", offer.price);
                    intent.putExtra("long_description", offer.long_description);
                    intent.putExtra("lat", offer.lat);
                    intent.putExtra("lng", offer.lng);
                    context.startActivity(intent);
                }
            });

            ovh.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    offers.remove(position);
                    notifyItemRemoved(position);
                }
            });

            ovh.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    offers.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void addOffers(List<Offer> offers) {
        int lastOffer = offers.size(), changed = 0;
        for (Offer o : offers) {
            if (!this.offers.contains(o)) {
                this.offers.add(o);
                changed++;
            }
        }
        notifyItemRangeChanged(lastOffer, changed);
    }
}
