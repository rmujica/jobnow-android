package com.wuqi.jobnow.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuqi.jobnow.JobnowApplication;
import com.wuqi.jobnow.R;
import com.wuqi.jobnow.activities.DetailActivity;
import com.wuqi.jobnow.activities.LoginActivity;
import com.wuqi.jobnow.entities.Offer;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link PagerAdapter} that represents each page as a {@link View}.
 *
 * @author Sebastian Kaspari <sebastian@androidzeitgeist.com>
 */
public class OffersAdapter extends PagerAdapter
{
    private List<Offer> offers = new LinkedList<Offer>();
    private Context context;

    public OffersAdapter(Context context) {
        this.context = context;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position The position of the item within the adapter's data set of the item whose view we want.
     * @param pager    The ViewPager that this view will eventually be attached to.
     *
     * @return A View corresponding to the data at the specified position.
     */
    public View getView(int position, ViewPager pager) {
        // USE HOLDER VIEW
        final Offer o = offers.get(position);

        View v = LayoutInflater.from(JobnowApplication.getInstance())
                .inflate(R.layout.detail_offer, pager, false);


       //Get the views elements
        TextView price = (TextView) v.findViewById(R.id.price);
        TextView short_description = (TextView) v.findViewById(R.id.short_description);
        ImageView info = (ImageView) v.findViewById(R.id.info);
        ImageView icon = (ImageView) v.findViewById(R.id.icon_image);

        //Button to Activity DetailsActivity with offer attributes as parameters
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("short_description",o.short_description);
                intent.putExtra("price",o.price);
                intent.putExtra("long_description",o.long_description);
                context.startActivity(intent);
            }
        });

        if (o != null) {
            price.setText("$" + o.price);
            short_description.setText(o.short_description);

            //Change Text size according to short_description length
            if( short_description.length() >= 30){
                short_description.setTextSize(30);
            }

             //Change Image according to category
            if(o.category.equals("1")){
                icon.setImageResource(R.drawable.categoria1a);
            }
            if(o.category.equals("2")){
                icon.setImageResource(R.drawable.categoria2a);
            }
            if(o.category.equals("3")){
                icon.setImageResource(R.drawable.categoria3a);
            }
            if(o.category.equals("4")){
                icon.setImageResource(R.drawable.categoria4a);
            }
            if(o.category.equals("5")){
                icon.setImageResource(R.drawable.categoria5a);
            }
        } else {
            Log.d("com.wuqi.jobnow", "offer is null in offersadapter");
        }

        return v;
    }

    public void addOffers(List<Offer> offers) {
        boolean changed = false;
        for (Offer o : offers) {
            if (!this.offers.contains(o)) {
                this.offers.add(o);
                changed = true;
            }
        }

        if (changed) notifyDataSetChanged();
    }

    /**
     * Determines whether a page View is associated with a specific key object as
     * returned by instantiateItem(ViewGroup, int).
     *
     * @param view   Page View to check for association with object
     * @param object Object to check for association with view
     *
     * @return true if view is associated with the key object object.
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    /**
     * Create the page for the given position.
     *
     * @param container The containing View in which the page will be shown.
     * @param position  The page position to be instantiated.
     *
     * @return Returns an Object representing the new page. This does not need
     *         to be a View, but can be some other container of the page.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewPager pager = (ViewPager) container;
        View view = getView(position, pager);
        pager.addView(view);

        return view;
    }

    /**
     * Remove a page for the given position.
     *
     * @param container The containing View from which the page will be removed.
     * @param position  The page position to be removed.
     * @param view      The same object that was returned by instantiateItem(View, int).
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
    }

}