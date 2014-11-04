package com.wuqi.jobnow.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuqi.jobnow.R;

public class OfferViewHolder extends RecyclerView.ViewHolder {

    public View container;
    public TextView price, short_description, status;
    public ImageView icon, info, ok, cancel, category;

    public OfferViewHolder(View stickerView, boolean isSlim) {
        super(stickerView);

        if (!isSlim) {
            price = (TextView) stickerView.findViewById(R.id.price);
            short_description = (TextView) stickerView.findViewById(R.id.short_description);
            icon = (ImageView) stickerView.findViewById(R.id.icon_image);
            info = (ImageView) stickerView.findViewById(R.id.info);
            ok = (ImageView) stickerView.findViewById(R.id.ok);
            cancel = (ImageView) stickerView.findViewById(R.id.cancel);
        } else {
            container = itemView.findViewById(R.id.container);
            category = (ImageView) itemView.findViewById(R.id.category);
            short_description = (TextView) itemView.findViewById(R.id.title);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
