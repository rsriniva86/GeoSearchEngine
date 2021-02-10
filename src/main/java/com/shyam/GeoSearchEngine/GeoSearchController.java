package com.shyam.GeoSearchEngine;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.core.geosearchengine.*;
import com.shyam.GeoSearchEngine.models.json.Geolocation;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Map<String, Object> getData() throws Exception {
        TestDataFetcher fetcher = new TestDataFetcher();
        Map<String,Object> returnMap=new HashMap<String,Object>();
        returnMap.put("success",true);
        returnMap.put("content",fetcher.get(testDataRepository));
        return returnMap;
    }

    @PostMapping("data/search")
    public @ResponseBody Map<String, Object> searchDataWithinDistance(@RequestBody Geopoint geoPoint) throws Exception {
        TestDataFetcher fetcher = new TestDataFetcher();
        Map<String,Object> returnMap=new HashMap<String,Object>();
        returnMap.put("success",true);
        returnMap.put("content",fetcher.get(testDataRepository, geoLocationRepository,geoPoint));
        return returnMap;
    }

    @GetMapping("data/stats")
    public Map<String, Object> nameStats(@RequestParam String name) throws Exception {
        TestDataStatsFetcher fetcher = new TestDataStatsFetcher();
        Map<String,Object> returnMap=new HashMap<String,Object>();
        returnMap.put("success",true);
        returnMap.put("content",fetcher.get(testDataRepository, name));
        return returnMap;
    }

    @PutMapping("/data/{id}")
    public @ResponseBody
    Map<String, Object> updateGeoPointForData(@PathVariable int id,
                                                      @RequestBody Geolocation geoLocation)  {
        TestDataUpdator testDataUpdator = new TestDataUpdator();
        Map<String,Object> returnMap=new HashMap<String,Object>();
        returnMap.put("success",true);
        try {
            returnMap.put("content",
                    testDataUpdator.update(testDataRepository,
                            geoLocationRepository,
                            id,
                            geoLocation,
                            false));
        }catch (GeoSearchEngineException e) {
            returnMap.put("success",false);
            returnMap.put("error",new GeoSearchEngineError(e.getCode().name(),e.getMessage()));
        }

        catch (Exception e) {
            returnMap.put("success",false);
            returnMap.put("error",new GeoSearchEngineError(GeoSearchEngineErrorCode.GENERIC.name(),e.getMessage()));
        }
        return returnMap;
    }
    @PutMapping("/data/{id}/overwrite")
    public @ResponseBody
    Map<String, Object> updateGeoPointForDataOverwrite(@PathVariable int id,
                                                               @RequestBody Geolocation geoLocation) throws Exception {
        TestDataUpdator testDataUpdator = new TestDataUpdator();
        Map<String,Object> returnMap=new HashMap<String,Object>();
        returnMap.put("success",true);
        returnMap.put("content",testDataUpdator.update(testDataRepository, geoLocationRepository,id, geoLocation,true));
        return returnMap;

    }

}
