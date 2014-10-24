package com.wuqi.jobnow.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User implements Parcelable {
    @SerializedName("_id") public String id;
    public String type;
    public String email;
    public String password;
    public String first_name;
    public String last_name;
    public String birth_date;
    public String location;
    public String occupation;
    public String facebook_user;
    public List<String> applications;

    public User(String type, String email, String password, String first_name, String last_name, String birth_date, String location, String id, String occupation, String facebook_user, List<String> applications ) {
        this.id = id;
        this.type = type;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.location = location;
        this.occupation = occupation;
        this.facebook_user = facebook_user;
        this.applications = applications;
    }

    public User(Parcel in) {
        id = in.readString();
        type = in.readString();
        email = in.readString();
        password = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        location = in.readString();
        occupation = in.readString();
        facebook_user = in.readString();
        in.readStringList(applications);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(location);
        dest.writeString(occupation);
        dest.writeString(facebook_user);
        dest.writeStringList(applications);

    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };



}
