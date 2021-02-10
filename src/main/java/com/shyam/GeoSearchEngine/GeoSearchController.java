package com.shyam.GeoSearchEngine;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.core.geosearchengine.PlacesFetcher;
import com.shyam.GeoSearchEngine.core.geosearchengine.PlacesStatsFetcher;
import com.shyam.GeoSearchEngine.core.geosearchengine.PlacesUpdator;
import com.shyam.GeoSearchEngine.models.json.GeoLocation;
import com.shyam.GeoSearchEngine.models.json.GeoPoint;
import com.shyam.GeoSearchEngine.models.db.DBPlace;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/geoSearch")
public class GeoSearchController {

    @Autowired
    private PlacesRepository placesRepository;
    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @GetMapping("places")
    public Map<String, List<TestData>> getPlaces() throws Exception {
        PlacesFetcher fetcher = new PlacesFetcher();
        return fetcher.get(placesRepository);
    }

    @PostMapping("places/search")
    public @ResponseBody Map<String, List<TestData>> searchPlacesWithinDistance(@RequestBody GeoPoint geoPoint) throws Exception {
        PlacesFetcher fetcher = new PlacesFetcher();
        return fetcher.get(placesRepository, geoLocationRepository,geoPoint);
    }

    @GetMapping("places/stats")
    public ObjectNode nameStats(@RequestParam String name) throws Exception {
        PlacesStatsFetcher fetcher = new PlacesStatsFetcher();
        return fetcher.get(placesRepository, name);
    }

    @PutMapping("/places/{id}")
    public @ResponseBody
    Map<String, List<TestData>> updateGeoPointForPlaces(@PathVariable int id,
                                    @RequestBody GeoLocation geoLocation) throws Exception {
        PlacesUpdator placesUpdator = new PlacesUpdator();
        return placesUpdator.update(placesRepository, geoLocationRepository,id, geoLocation, false);
    }
    @PutMapping("/places/{id}/overwrite")
    public @ResponseBody
    Map<String, List<TestData>> updateGeoPointForPlacesOverwrite(@PathVariable int id,
                                             @RequestBody GeoLocation geoLocation) throws Exception {
        PlacesUpdator placesUpdator = new PlacesUpdator();
        return placesUpdator.update(placesRepository, geoLocationRepository,id, geoLocation,true);
    }

}
