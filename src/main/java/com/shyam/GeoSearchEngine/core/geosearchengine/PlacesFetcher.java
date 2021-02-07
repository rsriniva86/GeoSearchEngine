package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.models.GeoPoint;
import com.shyam.GeoSearchEngine.models.Place;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;

import java.util.List;
import java.util.Map;

public class PlacesFetcher {

    public String get(final PlacesRepository placesRepository)  {
        long startTime = System.currentTimeMillis();
        Iterable<Place> places = placesRepository.findAll();
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        Map<String, List<Place>> placesByLocation = PlacesJSONHandler.INSTANCE.groupPlacesByLocation(places);
        return PlacesJSONHandler.INSTANCE.convertPlacesByLocationToJSON(placesByLocation);
    }

    public String get(PlacesRepository placesRepository, GeoPoint geoPoint) {
        long startTime = System.currentTimeMillis();
        System.out.println(" Latitude::" + geoPoint.getLatitude());
        System.out.println("Longitude::" + geoPoint.getLongitude());
        Iterable<Place> places = placesRepository.findWithinXKms(
                geoPoint.getLatitude(),
                geoPoint.getLongitude(),
                AppConfiguration.DISTANCE_RANGE
        );
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        Map<String, List<Place>> placesByLocation = PlacesJSONHandler.INSTANCE.groupPlacesByLocation(places);
        return PlacesJSONHandler.INSTANCE.convertPlacesByLocationToJSON(placesByLocation);
    }
}
