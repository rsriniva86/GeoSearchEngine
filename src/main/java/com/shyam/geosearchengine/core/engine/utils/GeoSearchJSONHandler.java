package com.shyam.geosearchengine.core.engine.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.geosearchengine.core.JSONConstants;
import com.shyam.geosearchengine.dto.GeoInfoResponseDto;
import com.shyam.geosearchengine.dto.GeopointResponseDto;
import com.shyam.geosearchengine.models.GeoInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;

/**
 * This is a utility class for operations related to JSON creation and formatting
 */
public enum GeoSearchJSONHandler {
    INSTANCE;

    Logger logger= LogManager.getLogger(GeoSearchJSONHandler.class);
    /**
     * This method converts a list of TestDataDB into Map of String and List<TestData>
     * The key for the map is the location.
     * The value for the map is the different test data (including geo points) for that location.
     *
     * @param geoInfos
     * @return
     */

    public Map<String, List<GeoInfoResponseDto>> groupByLocation(Iterable<GeoInfo> geoInfos) {
        List<GeoInfo> geoInfoList = StreamSupport
                .stream(geoInfos.spliterator(), false)
                .collect(Collectors.toList());
        logger.info(geoInfoList);
        Map<String,List<GeoInfoResponseDto>> map= geoInfoList
                .stream()
                .map(geoInfo -> new GeoInfoResponseDto(
                        geoInfo.getId(),
                        geoInfo.getName(),
                        geoInfo.getGeoLocation().getLocation(),
                        new GeopointResponseDto(
                                geoInfo.getGeoLocation().getLatitude(),
                                geoInfo.getGeoLocation().getLongitude()
                        )
                ))
                .collect(groupingBy(GeoInfoResponseDto::getLocation));
        logger.info(map);
        return map;

    }

    /**
     * This is a simple utility method to create a ObjectNode JSOn for the exactNameMatch and partialNameMatch.
     *
     * @param exactNameMatch
     * @param partialNameMatch
     * @return
     */
    public ObjectNode createStatsJSON(int exactNameMatch, int partialNameMatch) {
        logger.info("createStatsJSON");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put(JSONConstants.EXACT_MATCHCOUNT_TAG, exactNameMatch);
        rootNode.put(JSONConstants.MATCHCOUNT_TAG, partialNameMatch);
        logger.info(rootNode);
        return rootNode;
    }
}
