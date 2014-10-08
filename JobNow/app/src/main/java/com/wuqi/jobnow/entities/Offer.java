package com.wuqi.jobnow.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offer {
    public String business_name;
    public String category;
    public String short_description;
    public String long_description;
    public String price;
    public String price_type;
    public List<String> candidates;
    @SerializedName("_id") public String id;
    public Double lat;
    public Double lng;
}
