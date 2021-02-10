package com.shyam.GeoSearchEngine.core.geosearchengine.operations;

import com.shyam.GeoSearchEngine.core.geosearchengine.utils.GeoSearchJSONHandler;
import com.shyam.GeoSearchEngine.models.db.TestDataDB;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for fetching all the test data in DB
 */
public class TestDataFetcher implements GeoSearchEngineOperation {

    private static final Logger logger = LogManager.getLogger(TestDataFetcher.class);

    private final TestDataRepository testDataRepository;

    public TestDataFetcher(TestDataRepository testDataRepository) {
        this.testDataRepository = testDataRepository;
    }

    @Override
    public Object doOperation() throws Exception {

        long startTime = System.currentTimeMillis();
        Iterable<TestDataDB> testDataPoints = testDataRepository.findAll();
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken = " + timeTaken);
        Object object =
                GeoSearchJSONHandler
                        .INSTANCE
                        .groupByLocation(testDataPoints);
        return object;
    }


}
