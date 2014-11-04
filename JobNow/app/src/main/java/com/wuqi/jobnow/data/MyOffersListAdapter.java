package com.wuqi.jobnow.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuqi.jobnow.R;
import com.wuqi.jobnow.activities.DetailActivity;
import com.wuqi.jobnow.entities.Offer;

import java.util.ArrayList;
import java.util.List;

public class MyOffersListAdapter extends RecyclerView.Adapter<MyOffersViewHolder> {
    Context context;
    String uid;
    private List<Offer> offers = new ArrayList<Offer>(150);

    public MyOffersListAdapter(Context context, String uid) {
        this.context = context;
        this.uid = uid;
    }

    @Override
    public MyOffersViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
        View view = LayoutInflater.from(viewHolder.getContext())
                .inflate(R.layout.detail_offer_slim, viewHolder, false);
        return new MyOffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOffersViewHolder viewHolder, int position) {
        final Offer currentOffer = offers.get(position);

        //Change short description size
        String shorter_description;
        if (currentOffer.short_description.length() >= 25) {
            shorter_description = currentOffer.short_description.substring(0, 25) + "...";
        } else {
            shorter_description = currentOffer.short_description;
        }
        viewHolder.title.setText(shorter_description);

        switch (Integer.parseInt(currentOffer.category)) {
            case 1:
                viewHolder.category.setImageResource(R.drawable.categoria1a);
                break;
            case 2:
                viewHolder.category.setImageResource(R.drawable.categoria2a);
                break;
            case 3:
                viewHolder.category.setImageResource(R.drawable.categoria3a);
                break;
            case 4:
                viewHolder.category.setImageResource(R.drawable.categoria4a);
                break;
            case 5:
                viewHolder.category.setImageResource(R.drawable.categoria5a);
                break;
        }

        switch (Integer.parseInt(currentOffer.state)) {
            case 1:
                viewHolder.status.setText("Pendiente");
                viewHolder.status.setTextColor(Color.BLACK);
                break;
            case 2:
                viewHolder.status.setText("Aceptado");
                viewHolder.status.setTextColor(Color.GREEN);
                break;
            case 3:
                viewHolder.status.setText("Rechazado");
                viewHolder.status.setTextColor(Color.RED);
                break;
        }

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("short_description", currentOffer.short_description);
                intent.putExtra("price", currentOffer.price);
                intent.putExtra("long_description", currentOffer.long_description);
                intent.putExtra("lat", currentOffer.lat);
                intent.putExtra("lng", currentOffer.lng);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void addOffers(List<Offer> offers) {
        int initial = this.offers.size(), count = 0;
        for (Offer o : offers) {
            if (o.candidates.contains(uid)) {
                o.state = "1";
                this.offers.add(o);
                count++;
            } else if (o.accepted.contains(uid)) {
                o.state = "2";
                this.offers.add(o);
                count++;
            } else if (o.rejected.contains(uid)) {
                o.state = "3";
                this.offers.add(o);
                count++;
            }
        }

        notifyItemRangeInserted(initial, count);
    }
}
