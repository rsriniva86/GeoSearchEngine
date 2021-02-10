package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.core.engine.utils.GeoSearchJSONHandler;
import com.shyam.geosearchengine.models.GeoInfo;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for fetching all the test data in DB
 */
public class GeoInfoFetcher implements GeoSearchEngineOperation {

    private static final Logger logger = LogManager.getLogger(GeoInfoFetcher.class);

    private final GeoInfoRepository geoInfoRepository;

    public GeoInfoFetcher(GeoInfoRepository geoInfoRepository) {
        this.geoInfoRepository = geoInfoRepository;
    }

    @Override
    public Object doOperation() throws Exception {

        long startTime = System.currentTimeMillis();
        Iterable<GeoInfo> geoInfos = geoInfoRepository.findAll();
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken = " + timeTaken);
        Object object =
                GeoSearchJSONHandler
                        .INSTANCE
                        .groupByLocation(geoInfos);
        return object;
    }


}
