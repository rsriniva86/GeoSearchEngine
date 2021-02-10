package com.shyam.GeoSearchEngine.core.geosearchengine.operations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.core.geosearchengine.operations.GeoSearchEngineOperation;
import com.shyam.GeoSearchEngine.core.geosearchengine.utils.GeoSearchJSONHandler;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestDataStatsFetcher implements GeoSearchEngineOperation {

    private final TestDataRepository testDataRepository;
    private final String name;
    private final Logger logger= LogManager.getLogger(TestDataUpdator.class);

    public TestDataStatsFetcher(TestDataRepository testDataRepository,String name){
        this.testDataRepository=testDataRepository;
        this.name=name;
    }

    @Override
    public Object doOperation() throws Exception {
        long startTime = System.currentTimeMillis();
        int exactNameMatch = testDataRepository.findExactNameMatch(name);
        int partialNameMatch = testDataRepository.findPartialNameMatch(name);
        ObjectNode rootNode= GeoSearchJSONHandler.INSTANCE.createStatsJSON(exactNameMatch,partialNameMatch);
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken = " + timeTaken);
        return rootNode;    }
}
