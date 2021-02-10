package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.models.db.DBPlace;
import com.shyam.GeoSearchEngine.models.json.GeoPoint;
import com.shyam.GeoSearchEngine.models.json.TestData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;

public enum PlacesJSONHandler {
    INSTANCE;


    public String convertPlacesByLocationToJSON(Map<String, List<TestData>> placesByLocation) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(placesByLocation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Map<String, List<TestData>> groupPlacesByLocation(Iterable<DBPlace> places) {
        List<DBPlace> DBPlaceList = StreamSupport
                .stream(places.spliterator(), false)
                .collect(Collectors.toList());

        return DBPlaceList
                .stream()

                .map(dbPlace -> new TestData(
                        dbPlace.getId(),
                        dbPlace.getName(),
                        dbPlace.getGeoLocation().getLocation(),
                        new GeoPoint(
                                dbPlace.getGeoLocation().getLatitude(),
                                dbPlace.getGeoLocation().getLongitude()
                                )
                ))
                .collect(groupingBy(TestData::getLocation));

    }

    public ObjectNode createStatsJSON(int exactNameMatch, int partialNameMatch) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("exactMatchCount", exactNameMatch);
        rootNode.put("matchCount", partialNameMatch);
       return rootNode;
    }
}
