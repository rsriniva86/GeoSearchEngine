package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.models.GeoPoint;
import com.shyam.GeoSearchEngine.models.Place;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;

public class PlacesUpdator {
    public Place update(PlacesRepository placesRepository, int id, GeoPoint geoPoint) {

        long startTime = System.currentTimeMillis();
        System.out.println("id::" + id);

        System.out.println("Latitude::" + geoPoint.getLatitude());
        System.out.println("Longitude::" + geoPoint.getLongitude());
        Place place = placesRepository.findById(id);
        place.getGeoLocation().setLatitude(geoPoint.getLatitude());
        place.getGeoLocation().setLongitude(geoPoint.getLongitude());
        placesRepository.save(place);
        boolean isUpdated = (place.getGeoLocation().getLatitude() == geoPoint.getLatitude());
        System.out.println("isUpdated? " + isUpdated);
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return place;
    }
}
