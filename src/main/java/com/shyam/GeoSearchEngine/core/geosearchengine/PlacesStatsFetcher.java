package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;

public class PlacesStatsFetcher {
    public String get(final PlacesRepository placesRepository, final String name) throws JsonProcessingException {
        long startTime = System.currentTimeMillis();
        int exactNameMatch = placesRepository.findExactNameMatch(name);
        int partialNameMatch = placesRepository.findPartialNameMatch(name);
        String jsonString=PlacesJSONHandler.INSTANCE.createStatsJSON(exactNameMatch,partialNameMatch);
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return jsonString;
    }
}
