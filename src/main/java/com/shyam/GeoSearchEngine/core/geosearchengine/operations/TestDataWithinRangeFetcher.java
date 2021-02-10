package com.shyam.GeoSearchEngine.core.geosearchengine.operations;

import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.core.geosearchengine.utils.GeoSearchJSONHandler;
import com.shyam.GeoSearchEngine.models.db.DBGeoLocation;
import com.shyam.GeoSearchEngine.models.db.TestDataDB;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TestDataWithinRangeFetcher implements GeoSearchEngineOperation {


    private final TestDataRepository testDataRepository;
    private final GeoLocationRepository geoLocationRepository;
    private final Geopoint geopoint;
    public TestDataWithinRangeFetcher(TestDataRepository testDataRepository,
                                      GeoLocationRepository geoLocationRepository,
                                      Geopoint geoPoint){
        this.testDataRepository=testDataRepository;
        this.geoLocationRepository=geoLocationRepository;
        this.geopoint=geoPoint;

    }

    @Override
    public Object doOperation() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println(" Latitude::" + geopoint.getLatitude());
        System.out.println("Longitude::" + geopoint.getLongitude());
        Iterable<DBGeoLocation> geoLocations=geoLocationRepository.findWithinXKms(
                geopoint.getLatitude(),
                geopoint.getLongitude(),
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
