package com.shyam.GeoSearchEngine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name="PLACES")
public class Place {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "location_id", insertable = false, updatable = false)
    private Long location_id;

    @ManyToOne
    @JoinColumn(name="location_id", referencedColumnName = "id")
    private GeoLocation geoLocation;



    protected Place() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }


    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }

    @Override
    public String toString() {
        return String.format(
                "Place[id=%d, name='%s', location='%s',latitude='%f', longitude='%f']",
                id, name, geoLocation.getLocation(),geoLocation.getLatitude(),geoLocation.getLongitude());
    }

}
