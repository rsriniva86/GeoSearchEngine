package com.shyam.GeoSearchEngine.models.db;

import javax.persistence.*;

@Entity(name = "TESTDATA")
public class TestDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "location_id")
    private Long location_id;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", insertable = false, updatable = false)
    private GeoLocationDB geoLocation;


    protected TestDataDB() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoLocationDB getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocationDB geoLocation) {
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
                id, name, geoLocation.getLocation(), geoLocation.getLatitude(), geoLocation.getLongitude());
    }

}
