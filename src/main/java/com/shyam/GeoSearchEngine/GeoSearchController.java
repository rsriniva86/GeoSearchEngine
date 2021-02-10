package com.shyam.GeoSearchEngine;

import com.shyam.GeoSearchEngine.core.geosearchengine.GeoSearchResponseWrapper;
import com.shyam.GeoSearchEngine.core.geosearchengine.operations.*;
import com.shyam.GeoSearchEngine.models.json.Geolocation;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/geoSearch")
public class GeoSearchController {

    @Autowired
    private TestDataRepository testDataRepository;
    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @GetMapping("data/all")
    public Map<String, Object> getData() {
        GeoSearchEngineOperation operation = new TestDataFetcher(testDataRepository);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }

    @PostMapping("data/search")
    public @ResponseBody
    Map<String, Object> searchDataWithinDistance(@RequestBody Geopoint geopoint) throws Exception {
        GeoSearchEngineOperation operation = new TestDataWithinRangeFetcher(testDataRepository, geoLocationRepository, geopoint);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }

    @GetMapping("data/stats")
    public Map<String, Object> nameStats(@RequestParam String name) {
        GeoSearchEngineOperation operation = new TestDataStatsFetcher(testDataRepository, name);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }

    @PutMapping("/data/{id}")
    public @ResponseBody
    Map<String, Object> updateGeoPointForData(@PathVariable int id,
                                              @RequestBody Geolocation geoLocation) {
        GeoSearchEngineOperation operation = new TestDataUpdator(testDataRepository,
                geoLocationRepository,
                id,
                geoLocation,
                false);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }

    @PutMapping("/data/{id}/overwrite")
    public @ResponseBody
    Map<String, Object> updateGeoPointForDataOverwrite(@PathVariable int id,
                                                       @RequestBody Geolocation geoLocation) throws Exception {

        GeoSearchEngineOperation operation = new TestDataUpdator(testDataRepository,
                geoLocationRepository,
                id,
                geoLocation,
                true);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }

}
