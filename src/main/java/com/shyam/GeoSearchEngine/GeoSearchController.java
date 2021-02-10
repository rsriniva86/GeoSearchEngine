package com.shyam.GeoSearchEngine;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.core.geosearchengine.TestDataFetcher;
import com.shyam.GeoSearchEngine.core.geosearchengine.TestDataStatsFetcher;
import com.shyam.GeoSearchEngine.core.geosearchengine.TestDataUpdator;
import com.shyam.GeoSearchEngine.models.json.Geolocation;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/geoSearch")
public class GeoSearchController {

    @Autowired
    private TestDataRepository testDataRepository;
    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @GetMapping("data")
    public Map<String, List<TestData>> getData() throws Exception {
        TestDataFetcher fetcher = new TestDataFetcher();
        return fetcher.get(testDataRepository);
    }

    @PostMapping("data/search")
    public @ResponseBody Map<String, List<TestData>> searchDataWithinDistance(@RequestBody Geopoint geoPoint) throws Exception {
        TestDataFetcher fetcher = new TestDataFetcher();
        return fetcher.get(testDataRepository, geoLocationRepository,geoPoint);
    }

    @GetMapping("data/stats")
    public ObjectNode nameStats(@RequestParam String name) throws Exception {
        TestDataStatsFetcher fetcher = new TestDataStatsFetcher();
        return fetcher.get(testDataRepository, name);
    }

    @PutMapping("/data/{id}")
    public @ResponseBody
    Map<String, List<TestData>> updateGeoPointForData(@PathVariable int id,
                                                      @RequestBody Geolocation geoLocation) throws Exception {
        TestDataUpdator testDataUpdator = new TestDataUpdator();
        return testDataUpdator.update(testDataRepository, geoLocationRepository,id, geoLocation, false);
    }
    @PutMapping("/data/{id}/overwrite")
    public @ResponseBody
    Map<String, List<TestData>> updateGeoPointForDataOverwrite(@PathVariable int id,
                                                               @RequestBody Geolocation geoLocation) throws Exception {
        TestDataUpdator testDataUpdator = new TestDataUpdator();
        return testDataUpdator.update(testDataRepository, geoLocationRepository,id, geoLocation,true);
    }

}
