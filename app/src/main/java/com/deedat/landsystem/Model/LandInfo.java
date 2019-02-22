package com.deedat.landsystem.Model;

public class LandInfo {
    private String landcode,dimen,location,owner_name;

    public LandInfo(String landcode, String dimen, String location, String owner_name) {
        this.landcode = landcode;
        this.dimen = dimen;
        this.location = location;
        this.owner_name = owner_name;
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
}
