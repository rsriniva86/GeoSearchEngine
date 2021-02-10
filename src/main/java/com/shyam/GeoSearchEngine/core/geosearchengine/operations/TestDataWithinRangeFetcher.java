package com.shyam.GeoSearchEngine.core.geosearchengine.operations;

import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.core.geosearchengine.utils.GeoSearchJSONHandler;
import com.shyam.GeoSearchEngine.models.db.DBGeoLocation;
import com.shyam.GeoSearchEngine.models.db.TestDataDB;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TestDataWithinRangeFetcher implements GeoSearchEngineOperation {

    private static final Logger logger= LogManager.getLogger(TestDataFetcher.class);
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
        logger.info(" Latitude::" + geopoint.getLatitude());
        logger.info("Longitude::" + geopoint.getLongitude());
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
        logger.info("Time Taken = " + timeTaken);
        return GeoSearchJSONHandler
                .INSTANCE
                .groupByLocation(testDataPoints);
    }
}
