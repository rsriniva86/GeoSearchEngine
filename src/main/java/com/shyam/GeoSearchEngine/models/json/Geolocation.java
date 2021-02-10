package com.shyam.GeoSearchEngine.models.json;

public class Geolocation {

    private String location;

    private Geopoint geopoint;

    public Geolocation(String location, Geopoint geopoint) {
        this.location = location;
        this.geopoint = geopoint;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Geopoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(Geopoint geoPoint) {
        this.geopoint = geoPoint;
    }
}
