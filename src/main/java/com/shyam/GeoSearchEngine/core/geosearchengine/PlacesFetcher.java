package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.models.db.DBGeoLocation;
import com.shyam.GeoSearchEngine.models.json.GeoPoint;
import com.shyam.GeoSearchEngine.models.db.DBPlace;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PlacesFetcher {

    public Map<String, List<TestData>> get(final PlacesRepository placesRepository)  {
        long startTime = System.currentTimeMillis();
        Iterable<DBPlace> places = placesRepository.findAll();
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return
                PlacesJSONHandler
                        .INSTANCE
                        .groupPlacesByLocation(places);

    }

    public Map<String, List<TestData>> get(PlacesRepository placesRepository, GeoLocationRepository geoLocationRepository,GeoPoint geoPoint) {
        long startTime = System.currentTimeMillis();
        System.out.println(" Latitude::" + geoPoint.getLatitude());
        System.out.println("Longitude::" + geoPoint.getLongitude());
        Iterable<DBGeoLocation> geoLocations=geoLocationRepository.findWithinXKms(
                geoPoint.getLatitude(),
                geoPoint.getLongitude(),
                AppConfiguration.DISTANCE_RANGE
        );
        List<Long> locationIDList = StreamSupport
                .stream(geoLocations.spliterator(), false)
                .map(DBGeoLocation::getId)
                .collect(Collectors.toList());

        Iterable<DBPlace> places=placesRepository.findWithinXKms(locationIDList);

        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return PlacesJSONHandler
                .INSTANCE
                .groupPlacesByLocation(places);

    }
}
