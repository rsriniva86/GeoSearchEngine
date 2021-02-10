package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.models.db.GeoLocation;
import com.shyam.GeoSearchEngine.models.json.GeoPoint;
import com.shyam.GeoSearchEngine.models.db.Place;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PlacesFetcher {

    public String get(final PlacesRepository placesRepository)  {
        long startTime = System.currentTimeMillis();
        Iterable<Place> places = placesRepository.findAll();
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        Map<String, List<Place>> placesByLocation = PlacesJSONHandler.INSTANCE.groupPlacesByLocation(places);
        return PlacesJSONHandler.INSTANCE.convertPlacesByLocationToJSON(placesByLocation);
    }

    public String get(PlacesRepository placesRepository, GeoLocationRepository geoLocationRepository,GeoPoint geoPoint) {
        long startTime = System.currentTimeMillis();
        System.out.println(" Latitude::" + geoPoint.getLatitude());
        System.out.println("Longitude::" + geoPoint.getLongitude());
        Iterable<GeoLocation> geoLocations=geoLocationRepository.findWithinXKms(
                geoPoint.getLatitude(),
                geoPoint.getLongitude(),
                AppConfiguration.DISTANCE_RANGE
        );
        List<Long> locationIDList = StreamSupport
                .stream(geoLocations.spliterator(), false)
                .map(GeoLocation::getId)
                .collect(Collectors.toList());

        Iterable<Place> places=placesRepository.findWithinXKms(locationIDList);

        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        Map<String, List<Place>> placesByLocation = PlacesJSONHandler
                .INSTANCE
                .groupPlacesByLocation(places);
        return PlacesJSONHandler.INSTANCE.convertPlacesByLocationToJSON(placesByLocation);
    }
}
