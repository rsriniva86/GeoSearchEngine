package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.core.AppConfiguration;
import com.shyam.geosearchengine.core.engine.utils.GeoSearchJSONHandler;
import com.shyam.geosearchengine.models.GeoLocation;
import com.shyam.geosearchengine.models.GeoInfo;
import com.shyam.geosearchengine.dto.GeopointResponseDto;
import com.shyam.geosearchengine.repositories.GeoLocationRepository;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GeoInfoRangeFetcher implements GeoSearchEngineOperation {

    private static final Logger logger = LogManager.getLogger(GeoInfoFetcher.class);
    private final GeoInfoRepository geoInfoRepository;
    private final GeoLocationRepository geoLocationRepository;
    private final GeopointResponseDto geopointResponseDto;

    public GeoInfoRangeFetcher(GeoInfoRepository geoInfoRepository,
                               GeoLocationRepository geoLocationRepository,
                               GeopointResponseDto geoPoint) {
        this.geoInfoRepository = geoInfoRepository;
        this.geoLocationRepository = geoLocationRepository;
        this.geopointResponseDto = geoPoint;

    }

    @Override
    public Object doOperation() throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("Latitude::" + geopointResponseDto.getLatitude());
        logger.info("Longitude::" + geopointResponseDto.getLongitude());
        Iterable<GeoLocation> geoLocations = geoLocationRepository.findWithinXKms(
                geopointResponseDto.getLatitude(),
                geopointResponseDto.getLongitude(),
                AppConfiguration.DISTANCE_RANGE
        );
        List<Long> locationIDList = StreamSupport
                .stream(geoLocations.spliterator(), false)
                .map(GeoLocation::getId)
                .collect(Collectors.toList());

        Iterable<GeoInfo> testDataPoints = geoInfoRepository.findWithinXKms(locationIDList);

        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken = " + timeTaken);
        return GeoSearchJSONHandler
                .INSTANCE
                .groupByLocation(testDataPoints);
    }
}
