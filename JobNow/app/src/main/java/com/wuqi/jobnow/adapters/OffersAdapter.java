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
        Iterator<Offer> i = offers.iterator();
        Iterator<String> j ; //iterator for candidates list string
        Iterator<String> g ; //iterator for accepted list string
        Iterator<String> h ; //iterator for rejected list string
        System.out.println(offers);
        Offer dummy = offers.get(0);

        String candidate;
        String accepted;
        String rejected;

        while (i.hasNext()) {
            dummy = i.next();

            j = dummy.candidates.iterator();
            while (j.hasNext()) {
                candidate = j.next();
                if(candidate.equals(id)) {
                    dummy.state="1";
                    this.offers.add(dummy);
                }
            }

            g = dummy.accepted.iterator();
            while (g.hasNext()) {
                accepted = g.next();
                if(accepted.equals(id)) {
                    dummy.state="2";
                    this.offers.add(dummy);
                }
            }

            h = dummy.rejected.iterator();
            while (h.hasNext()) {
                rejected = h.next();
                if(rejected.equals(id)) {
                    dummy.state="3";
                    this.offers.add(dummy);
                }
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
