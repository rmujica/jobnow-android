package com.wuqi.jobnow.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offer implements Parcelable {
    public String user_id;
    public String category;
    public String short_description;
    public String long_description;
    public String price;
    public String price_type;
    public List<String> candidates;
    public List<String> accepted;
    public List<String> rejected;
    @SerializedName("_id") public String id;
    public Double lat;
    public Double lng;
    public String state;

    public Offer(String user_id, String short_description, String long_description, String price, String price_type, String category, List<String> candidates, List<String> accepted, List<String> rejected, String id, Double lat, Double lng) {
        this.user_id = user_id;
        this.short_description = short_description;
        this.long_description = long_description;
        this.price = price;
        this.price_type = price_type;
        this.category = category;
        this.candidates = candidates;
        this.accepted = accepted;
        this.rejected = rejected;
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public Offer(Parcel in) {
        user_id = in.readString();
        short_description = in.readString();
        long_description = in.readString();
        price = in.readString();
        price_type = in.readString();
        category = in.readString();
        in.readStringList(candidates);
        in.readStringList(accepted);
        in.readStringList(rejected);
        id = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(short_description);
        dest.writeString(long_description);
        dest.writeString(price);
        dest.writeString(price_type);
        dest.writeString(category);
        dest.writeStringList(candidates);
        dest.writeStringList(accepted);
        dest.writeStringList(rejected);
        dest.writeString(id);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    public static final Parcelable.Creator<Offer> CREATOR = new Parcelable.Creator<Offer>(){
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
}
