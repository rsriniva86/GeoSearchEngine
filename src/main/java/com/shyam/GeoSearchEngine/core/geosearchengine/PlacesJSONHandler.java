package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.models.Place;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;

public enum PlacesJSONHandler {
    INSTANCE;

    public String convertPlacesByLocationToJSON(Map<String, List<Place>> placesByLocation) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(placesByLocation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Map<String, List<Place>> groupPlacesByLocation(Iterable<Place> places) {
        List<Place> placeList = StreamSupport
                .stream(places.spliterator(), false)
                .collect(Collectors.toList());

        return placeList
                .stream()
                .collect(
                        groupingBy(
                                place -> place.getGeoLocation().getLocation()
                        )
                );
    }

    public String createStatsJSON(int exactNameMatch, int partialNameMatch) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("exactMatchCount", exactNameMatch);
        rootNode.put("matchCount", partialNameMatch);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }
}
