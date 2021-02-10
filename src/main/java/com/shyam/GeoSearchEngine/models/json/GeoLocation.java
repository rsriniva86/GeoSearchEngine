package com.shyam.GeoSearchEngine.models.json;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class GeoLocation {

    private String location;

    private GeoPoint geopoint;

    public GeoLocation(String location, GeoPoint geopoint) {
        this.location = location;
        this.geopoint = geopoint;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public GeoPoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(GeoPoint geoPoint) {
        this.geopoint = geoPoint;
    }
}
