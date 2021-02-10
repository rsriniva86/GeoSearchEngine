package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;

public class TestDataStatsFetcher {
    public ObjectNode get(final TestDataRepository testDataRepository, final String name)  {
        long startTime = System.currentTimeMillis();
        int exactNameMatch = testDataRepository.findExactNameMatch(name);
        int partialNameMatch = testDataRepository.findPartialNameMatch(name);
        ObjectNode rootNode= GeoSearchJSONHandler.INSTANCE.createStatsJSON(exactNameMatch,partialNameMatch);
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return rootNode;
    }
}
