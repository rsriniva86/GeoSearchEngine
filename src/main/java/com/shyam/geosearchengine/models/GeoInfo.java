package com.shyam.geosearchengine.models;

import javax.persistence.*;

@Entity
@Table(name = "GEOINFOS")
public class GeoInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private GeoLocation geoLocation;

    public GeoInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    @Override
    public String toString() {
        return String.format(
                "GeoInfo[id=%d, name='%s', location='%s',latitude='%f', longitude='%f']",
                id, name, geoLocation.getLocation(), geoLocation.getLatitude(), geoLocation.getLongitude());
    }

}
