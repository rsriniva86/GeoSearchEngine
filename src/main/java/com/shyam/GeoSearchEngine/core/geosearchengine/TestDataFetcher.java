package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.models.db.DBGeoLocation;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.db.TestDataDB;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TestDataFetcher {

    public Map<String, List<TestData>> get(final TestDataRepository testDataRepository)  {
        long startTime = System.currentTimeMillis();
        Iterable<TestDataDB> testDataPoints = testDataRepository.findAll();
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return
                GeoSearchJSONHandler
                        .INSTANCE
                        .groupByLocation(testDataPoints);

    }

    public Map<String, List<TestData>> get(TestDataRepository testDataRepository, GeoLocationRepository geoLocationRepository, Geopoint geoPoint) {
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

        Iterable<TestDataDB> testDataPoints= testDataRepository.findWithinXKms(locationIDList);

        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return GeoSearchJSONHandler
                .INSTANCE
                .groupByLocation(testDataPoints);

    }
}
