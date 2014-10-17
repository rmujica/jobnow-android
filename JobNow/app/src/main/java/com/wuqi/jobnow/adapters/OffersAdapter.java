package com.wuqi.jobnow.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.wuqi.jobnow.R;
import com.wuqi.jobnow.entities.Offer;
import com.wuqi.jobnow.fragments.OfferFragment;

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

    @Override
    public int getCount() {
        return total;
    }

    public void addOffers(List<Offer> offers) {
        this.offers.addAll(offers);
        this.total = this.offers.size();
        notifyDataSetChanged();
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}
