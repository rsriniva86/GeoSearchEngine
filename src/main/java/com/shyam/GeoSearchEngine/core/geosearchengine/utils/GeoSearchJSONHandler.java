package com.shyam.GeoSearchEngine.core.geosearchengine.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.models.db.TestDataDB;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.json.TestData;

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

    /**
     * This method converts a list of TestDataDB into Map of String and List<TestData>
     * The key for the map is the location.
     * The value for the map is the different test data (including geo points) for that location.
     * @param testDataPoints
     * @return
     */

    public Map<String, List<TestData>> groupByLocation(Iterable<TestDataDB> testDataPoints) {
        List<TestDataDB> TestDataDBList = StreamSupport
                .stream(testDataPoints.spliterator(), false)
                .collect(Collectors.toList());

        return TestDataDBList
                .stream()

                .map(testDataDb -> new TestData(
                        testDataDb.getId(),
                        testDataDb.getName(),
                        testDataDb.getGeoLocation().getLocation(),
                        new Geopoint(
                                testDataDb.getGeoLocation().getLatitude(),
                                testDataDb.getGeoLocation().getLongitude()
                                )
                ))
                .collect(groupingBy(TestData::getLocation));

    }

    /**
     * This is a simple utility method to create a ObjectNode JSOn for the exactNameMatch and partialNameMatch.
     * @param exactNameMatch
     * @param partialNameMatch
     * @return
     */
    public ObjectNode createStatsJSON(int exactNameMatch, int partialNameMatch)  {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("exactMatchCount", exactNameMatch);
        rootNode.put("matchCount", partialNameMatch);
       return rootNode;
    }
}
