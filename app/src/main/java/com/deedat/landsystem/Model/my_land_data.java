package com.deedat.landsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class my_land_data implements Parcelable {
    String area,region,landcode,length,width;

    public my_land_data(String area, String region, String landcode, String length, String width) {
        this.area = area;
        this.region = region;
        this.landcode = landcode;
        this.length = length;
        this.width = width;
    }

    public String getArea() {
        return area;
    }

    public String getRegion() {
        return region;
    }

    public String getLandcode() {
        return landcode;
    }

    public String getLength() {
        return length;
    }

    public String getWidth() {
        return width;
    }

    public static Creator<my_land_data> getCREATOR() {
        return CREATOR;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLandcode(String landcode) {
        this.landcode = landcode;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    protected my_land_data(Parcel in) {
        area = in.readString();
        region = in.readString();
        landcode = in.readString();
        length = in.readString();
        width = in.readString();
    }

    public static final Creator<my_land_data> CREATOR = new Creator<my_land_data>() {
        @Override
        public my_land_data createFromParcel(Parcel in) {
            return new my_land_data(in);
        }

        @Override
        public my_land_data[] newArray(int size) {
            return new my_land_data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(area);
        dest.writeString(region);
        dest.writeString(landcode);
        dest.writeString(length);
        dest.writeString(width);
    }
}
