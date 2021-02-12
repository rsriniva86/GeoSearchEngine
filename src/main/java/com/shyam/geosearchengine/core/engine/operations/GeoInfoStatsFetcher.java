package com.shyam.geosearchengine.core.engine.operations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineErrorCode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineException;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineMessages;
import com.shyam.geosearchengine.core.engine.utils.GeoSearchJSONHandler;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeoInfoStatsFetcher implements GeoSearchEngineOperation {

    private final GeoInfoRepository geoInfoRepository;
    private final String name;
    private final Logger logger = LogManager.getLogger(GeoInfoStatsFetcher.class);

    public GeoInfoStatsFetcher(GeoInfoRepository geoInfoRepository, String name) {
        this.geoInfoRepository = geoInfoRepository;
        this.name = name;
    }

    @Override
    public Object doOperation() throws Exception {
        if (geoInfoRepository == null) {
            logger.error(GeoSearchEngineMessages.REPOSITORY_NOT_AVAILABLE);
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.REPOSITORY_NOT_AVAILABLE,
                    GeoSearchEngineMessages.REPOSITORY_NOT_AVAILABLE);
        }

        long startTime = System.currentTimeMillis();
        int exactNameMatch = geoInfoRepository.findExactNameMatch(name);
        int partialNameMatch = geoInfoRepository.findPartialNameMatch(name);
        ObjectNode rootNode = GeoSearchJSONHandler.INSTANCE.createStatsJSON(exactNameMatch, partialNameMatch);
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken = " + timeTaken);
        return rootNode;
    }
}
