package com.shyam.GeoSearchEngine.core.geosearchengine.operations;

import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.core.geosearchengine.operations.GeoSearchEngineOperation;
import com.shyam.GeoSearchEngine.core.geosearchengine.utils.GeoSearchJSONHandler;
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

public class TestDataFetcher implements GeoSearchEngineOperation {


    private final TestDataRepository testDataRepository;

    public TestDataFetcher(TestDataRepository testDataRepository){
        this.testDataRepository=testDataRepository;
    }

    @Override
    public Object doOperation() throws Exception {
        long startTime = System.currentTimeMillis();
        Iterable<TestDataDB> testDataPoints = testDataRepository.findAll();
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return
                GeoSearchJSONHandler
                        .INSTANCE
                        .groupByLocation(testDataPoints);
    }





}
