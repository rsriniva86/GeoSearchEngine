package com.shyam.GeoSearchEngine.models.json;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TestData {
    private long id;
    private String name;
    @JsonIgnore
    private String location;
    private GeoPoint geoPoint;

    public TestData(long id, String name, String location, GeoPoint geoPoint) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.geoPoint = geoPoint;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
