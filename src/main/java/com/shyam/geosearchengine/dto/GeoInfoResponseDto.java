package com.shyam.geosearchengine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GeoInfoResponseDto {
    private long id;
    private String name;
    @JsonIgnore
    private String location;
    private GeopointResponseDto geopointResponseDto;

    public GeoInfoResponseDto(long id, String name, String location, GeopointResponseDto geopointResponseDto) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.geopointResponseDto = geopointResponseDto;
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

    public GeopointResponseDto getGeopoint() {
        return geopointResponseDto;
    }

    public void setGeopoint(GeopointResponseDto geopointResponseDto) {
        this.geopointResponseDto = geopointResponseDto;
    }
}
