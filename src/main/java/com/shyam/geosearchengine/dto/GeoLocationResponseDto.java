package com.shyam.geosearchengine.dto;

public class GeoLocationResponseDto {

    private String location;

    private GeopointResponseDto geopointResponseDto;

    public GeoLocationResponseDto(String location, GeopointResponseDto geopointResponseDto) {
        this.location = location;
        this.geopointResponseDto = geopointResponseDto;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public GeopointResponseDto getGeopoint() {
        return geopointResponseDto;
    }

    public void setGeopoint(GeopointResponseDto geoPoint) {
        this.geopointResponseDto = geoPoint;
    }
}
