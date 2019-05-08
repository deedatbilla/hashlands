package com.deedat.landsystem.Model;

public class category {
    String category_name,thumbnail;
    public category(String category_name, String thumbnail) {
        this.category_name = category_name;
        this.thumbnail = thumbnail;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getCategory_name() {
        return category_name;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
