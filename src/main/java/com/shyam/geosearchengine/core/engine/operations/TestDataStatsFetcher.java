package com.shyam.geosearchengine.core.engine.operations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.geosearchengine.core.engine.utils.GeoSearchJSONHandler;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestDataStatsFetcher implements GeoSearchEngineOperation {

    private final GeoInfoRepository geoInfoRepository;
    private final String name;
    private final Logger logger = LogManager.getLogger(TestDataUpdator.class);

    public TestDataStatsFetcher(GeoInfoRepository geoInfoRepository, String name) {
        this.geoInfoRepository = geoInfoRepository;
        this.name = name;
    }

    @Override
    public Object doOperation() throws Exception {
        long startTime = System.currentTimeMillis();
        int exactNameMatch = geoInfoRepository.findExactNameMatch(name);
        int partialNameMatch = geoInfoRepository.findPartialNameMatch(name);
        ObjectNode rootNode = GeoSearchJSONHandler.INSTANCE.createStatsJSON(exactNameMatch, partialNameMatch);
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken = " + timeTaken);
        return rootNode;
    }
}
