package com.deedat.landsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class LandInfo implements Parcelable {
    private String landcode,owner_name,thumbnail, landregion,width,length, landarea;
public LandInfo(){

}
    public LandInfo(String landcode,  String owner_name, String thumbnail, String landregion, String width, String length, String landarea) {
        this.landcode = landcode;
        this.owner_name = owner_name;
        this.thumbnail = thumbnail;
        this.landregion = landregion;
        this.width = width;
        this.length = length;
        this.landarea = landarea;
    }

    public String getLandregion() {
        return landregion;
    }

    public String getWidth() {
        return width;
    }

    public String getLength() {
        return length;
    }

    public String getLandarea() {
        return landarea;
    }

    public void setLandregion(String landregion) {
        this.landregion = landregion;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setLandarea(String landarea) {
        this.landarea = landarea;
    }

    protected LandInfo(Parcel in) {
        landcode = in.readString();

        owner_name = in.readString();
        thumbnail = in.readString();
        landregion = in.readString();
        landarea = in.readString();
        length = in.readString();
        width = in.readString();
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

        dest.writeString(owner_name);
        dest.writeString(thumbnail);
        dest.writeString(landregion);
        dest.writeString(landarea);
        dest.writeString(length);
        dest.writeString(width);

    }
}
