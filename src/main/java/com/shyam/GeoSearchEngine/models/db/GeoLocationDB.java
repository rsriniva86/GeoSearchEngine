package com.shyam.GeoSearchEngine.models.db;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="GEOLOCATIONS")
public class GeoLocationDB {


    @Id
    //@SequenceGenerator(name= "GEO_LOCATION_SEQUENCE", sequenceName = "GEOLOCATIONS_SEQUENCE_ID", initialValue=1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String location;
    private double latitude;
    private double longitude;

    public GeoLocationDB() {
    }

    public GeoLocationDB(String location, double latitude, double longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GeoLocationDB)) {
            return false;
        }

        GeoLocationDB that = (GeoLocationDB) obj;

        return Objects.equals(this.location, that.location)
                && Objects.equals(this.latitude, that.latitude)
                && Objects.equals(this.id, that.id)
                && Objects.equals(this.longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, latitude,longitude,id);
    }
}
