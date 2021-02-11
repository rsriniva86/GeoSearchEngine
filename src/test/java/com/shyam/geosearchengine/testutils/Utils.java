package com.shyam.geosearchengine.testutils;

import com.shyam.geosearchengine.dto.GeoInfoResponseDto;
import com.shyam.geosearchengine.models.GeoInfo;
import com.shyam.geosearchengine.models.GeoLocation;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Utils {

    public static GeoInfo createGeoInfoObject(TestDataSet dataset) {
        GeoInfo geoInfo =new GeoInfo();
        geoInfo.setId(dataset.getId());
        geoInfo.setName(dataset.getName());
        geoInfo.setGeoLocation(new GeoLocation());
        geoInfo.getGeoLocation().setLocation(dataset.getLocation());
        geoInfo.getGeoLocation().setLatitude(dataset.getLatitude());
        geoInfo.getGeoLocation().setLongitude(dataset.getLongitude());
        return geoInfo;
    }

    public static void testGeoInfoResponseDto(GeoInfoResponseDto geoInfoResponseDto, TestDataSet dataSet) {
        assertTrue(geoInfoResponseDto.getId()==dataSet.getId(),
                "ID is wrong");
        assertTrue(geoInfoResponseDto.getName().equalsIgnoreCase(dataSet.getName()),
                "Name is wrong");
        assertTrue(geoInfoResponseDto.getLocation().equalsIgnoreCase(dataSet.getLocation()),
                "Location is wrong");
        assertTrue(geoInfoResponseDto.getGeopoint().getLatitude()== dataSet.getLatitude(),
                "Latitude is wrong");
        assertTrue(geoInfoResponseDto.getGeopoint().getLongitude()==dataSet.getLongitude(),
                "Longitude is wrong");
    }

    public static GeoLocation createGeoLocationObject(TestDataSet testDataSet) {
        GeoLocation geoLocation=new GeoLocation();
        geoLocation.setId(testDataSet.getLocationId());
        geoLocation.setLocation(testDataSet.getLocation());
        geoLocation.setLatitude(testDataSet.getLatitude());
        geoLocation.setLongitude(testDataSet.getLongitude());
        return geoLocation;
    }
}
