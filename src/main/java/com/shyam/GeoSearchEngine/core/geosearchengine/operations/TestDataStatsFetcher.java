package com.shyam.GeoSearchEngine.core.geosearchengine.operations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.core.geosearchengine.operations.GeoSearchEngineOperation;
import com.shyam.GeoSearchEngine.core.geosearchengine.utils.GeoSearchJSONHandler;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;

public class TestDataStatsFetcher implements GeoSearchEngineOperation {

    private final TestDataRepository testDataRepository;
    private final String name;

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
        System.out.println("Time Taken = " + timeTaken);
        return rootNode;    }
}
