package com.example.luisf.app4_foursquare;

/**
 * Created by luisf on 18/09/2017.
 */

public class Place {
    private String name;
    private String id;
    private String iconUrl;

    public Place(String name, String id, String iconUrl) {
        this.name = name;
        this.id = id;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
