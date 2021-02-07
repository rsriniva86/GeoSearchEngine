package com.thales.GeoSearchEngine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="PLACES")
public class Place {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String location;
    private double latitude;
    private double longitude;

    protected Place() {}

    public Place(String name, String location, double latitude, double longitude) {
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format(
                "Place[id=%d, name='%s', location='%s',latitude='%f', longitude='%f']",
                id, name, location,latitude,longitude);
    }

}
