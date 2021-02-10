package com.shyam.GeoSearchEngine.models.json;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TestData {
    private long id;
    private String name;
    @JsonIgnore
    private String location;
    private Geopoint geopoint;

    public TestData(long id, String name, String location, Geopoint geopoint) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.geopoint = geopoint;
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

    public Geopoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(Geopoint geopoint) {
        this.geopoint = geopoint;
    }
}
