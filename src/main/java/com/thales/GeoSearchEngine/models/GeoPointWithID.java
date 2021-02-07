package com.thales.GeoSearchEngine.models;

public class GeoPointWithID {

    private int id;
    private GeoPoint geoPoint;

    public GeoPointWithID(int id, GeoPoint geoPoint) {
        this.id = id;
        this.geoPoint = geoPoint;
    }

    public int getId() {
        return id;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
