package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.fasterxml.jackson.core.JsonProcessingException;
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

public enum GeoSearchJSONHandler {
    INSTANCE;

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

    public ObjectNode createStatsJSON(int exactNameMatch, int partialNameMatch)  {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("exactMatchCount", exactNameMatch);
        rootNode.put("matchCount", partialNameMatch);
       return rootNode;
    }
}
