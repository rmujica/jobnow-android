package com.wuqi.jobnow.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.wuqi.jobnow.R;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.fragments.OfferFragment;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OffersAdapter extends FragmentStatePagerAdapter {

    private Integer total = 0;
    private List<Offer> offers = new LinkedList<Offer>();
    private FragmentManager fragmentManager;

    public OffersAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        String name = makeFragmentName(R.id.pager, position);
        Fragment f = fragmentManager.findFragmentByTag(name);
        if (f == null)
            f = OfferFragment.newInstance(offers.get(position));
        return f;
    }

    public Offer getOffer(int position){
        return offers.get(position);
    }

    public void clearList(){
        offers.clear();
        notifyDataSetChanged();
    }

    public int getTotal(){
        return this.total;
    }

    @Override
    public int getCount() {
        return total;
    }

    public void addOffers(List<Offer> offers) {
        this.offers.addAll(offers);
        this.total = this.offers.size();
        notifyDataSetChanged();
    }

    public void addFilterOffers(List<Offer> offers, String id ) {
        System.out.println(offers.size());

        for (Offer o : offers) {
            if (o.candidates != null && o.candidates.contains(id)) {
                o.state = "1";
                this.offers.add(o);
            } else if (o.accepted != null && o.accepted.contains(id)) {
                o.state = "2";
                this.offers.add(o);
            } else if (o.rejected != null && o.rejected.contains(id)) {
                o.state = "3";
                this.offers.add(o);
            }
        }

        this.total = this.offers.size();

        notifyDataSetChanged();
        System.out.println(total);
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}
