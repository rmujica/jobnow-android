package com.wuqi.jobnow.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.wuqi.jobnow.R;
import com.wuqi.jobnow.entities.Offer;

import java.util.List;

public class SearchResultAdapter extends CursorAdapter {

    private final List<Offer> offers;
    TextView title;

    public SearchResultAdapter(Context context, Cursor cursor, List<Offer> offers) {
        super(context, cursor, false);

        this.offers = offers;
        Log.d("com.wuqi.jobnow", "search adapter created");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_offer_slim, parent, false);
        title = (TextView) view.findViewById(R.id.title);
        Log.d("com.wuqi.jobnow", "search adapter view created");
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        title.setText(offers.get(cursor.getPosition()).short_description);
        Log.d("com.wuqi.jobnow", "search adapter set");
    }
}
