package com.deedat.landsystem.Model;

public class LandInfo {
    private String landcode,dimen,location,owner_name,thumbnail;

    public LandInfo(String landcode, String dimen, String location, String owner_name,String thumbnail) {
        this.landcode = landcode;
        this.dimen = dimen;
        this.location = location;
        this.owner_name = owner_name;
        this.thumbnail=thumbnail;
    }

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
}
