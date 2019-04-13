package com.deedat.landsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class LandInfo implements Parcelable {
    private String landcode,dimen,location,owner_name,thumbnail,region;

    public LandInfo(String landcode, String dimen, String location, String owner_name,String thumbnail) {
        this.landcode = landcode;
        this.dimen = dimen;
        this.location = location;
        this.owner_name = owner_name;
        this.thumbnail=thumbnail;
    }

    protected LandInfo(Parcel in) {
        landcode = in.readString();
        dimen = in.readString();
        location = in.readString();
        owner_name = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<LandInfo> CREATOR = new Creator<LandInfo>() {
        @Override
        public LandInfo createFromParcel(Parcel in) {
            return new LandInfo(in);
        }

        @Override
        public LandInfo[] newArray(int size) {
            return new LandInfo[size];
        }
    };

    public String getLandcode() {
        return landcode;
    }

    public String getDimen() {
        return dimen;
    }

    public String getLocation() {
        return location;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(landcode);
        dest.writeString(dimen);
        dest.writeString(location);
        dest.writeString(owner_name);
        dest.writeString(thumbnail);
    }
}
