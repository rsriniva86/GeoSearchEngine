package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;

public class PlacesStatsFetcher {
    public ObjectNode get(final PlacesRepository placesRepository, final String name) throws JsonProcessingException {
        long startTime = System.currentTimeMillis();
        int exactNameMatch = placesRepository.findExactNameMatch(name);
        int partialNameMatch = placesRepository.findPartialNameMatch(name);
        ObjectNode rootNode=PlacesJSONHandler.INSTANCE.createStatsJSON(exactNameMatch,partialNameMatch);
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return rootNode;
    }
}
