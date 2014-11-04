package com.wuqi.jobnow.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuqi.jobnow.R;

public class MyOffersViewHolder extends RecyclerView.ViewHolder {

    View container;
    ImageView category;
    TextView title, status;

    public MyOffersViewHolder(View itemView) {
        super(itemView);

        container = itemView.findViewById(R.id.container);
        category = (ImageView) itemView.findViewById(R.id.category);
        title = (TextView) itemView.findViewById(R.id.title);
        status = (TextView) itemView.findViewById(R.id.status);
    }
}
