package com.shyam.GeoSearchEngine;

import com.shyam.GeoSearchEngine.core.geosearchengine.*;
import com.shyam.GeoSearchEngine.models.json.Geolocation;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/geoSearch")
public class GeoSearchController {

    @Autowired
    private TestDataRepository testDataRepository;
    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @GetMapping("data")
    public Map<String, Object> getData() throws Exception {
        GeoSearchEngineOperation operation = () -> {
            TestDataFetcher fetcher = new TestDataFetcher();
            return fetcher.get(testDataRepository);
        };
        return ResponseWrapper.INSTANCE.wrap(operation);
    }

    @PostMapping("data/search")
    public @ResponseBody Map<String, Object> searchDataWithinDistance(@RequestBody Geopoint geoPoint) throws Exception {
        GeoSearchEngineOperation operation = () -> {
            TestDataFetcher fetcher = new TestDataFetcher();
            return fetcher.get(testDataRepository, geoLocationRepository,geoPoint);
        };
        return ResponseWrapper.INSTANCE.wrap(operation);
    }

    @GetMapping("data/stats")
    public Map<String, Object> nameStats(@RequestParam String name) {

        GeoSearchEngineOperation operation = () -> {
            TestDataStatsFetcher fetcher = new TestDataStatsFetcher();
            return fetcher.get(testDataRepository, name);
        };
        return ResponseWrapper.INSTANCE.wrap(operation);
    }

    @PutMapping("/data/{id}")
    public @ResponseBody
    Map<String, Object> updateGeoPointForData(@PathVariable int id,
                                                      @RequestBody Geolocation geoLocation)  {
        GeoSearchEngineOperation operation = () -> {
            TestDataUpdator testDataUpdator = new TestDataUpdator();
            return testDataUpdator.update(testDataRepository,
                    geoLocationRepository,
                    id,
                    geoLocation,
                    false);
        };
        return ResponseWrapper.INSTANCE.wrap(operation);
    }
    @PutMapping("/data/{id}/overwrite")
    public @ResponseBody
    Map<String, Object> updateGeoPointForDataOverwrite(@PathVariable int id,
                                                               @RequestBody Geolocation geoLocation) throws Exception {

        GeoSearchEngineOperation operation = () -> {
            TestDataUpdator testDataUpdator = new TestDataUpdator();
            return testDataUpdator.update(testDataRepository,
                    geoLocationRepository,
                    id,
                    geoLocation,
                    true);
        };
        return ResponseWrapper.INSTANCE.wrap(operation);
    }

}
