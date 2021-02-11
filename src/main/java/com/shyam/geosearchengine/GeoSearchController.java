package com.shyam.geosearchengine;

import com.shyam.geosearchengine.core.engine.GeoSearchResponseWrapper;
import com.shyam.geosearchengine.core.engine.operations.*;
import com.shyam.geosearchengine.dto.GeoLocationResponseDto;
import com.shyam.geosearchengine.dto.GeopointResponseDto;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import com.shyam.geosearchengine.repositories.GeoLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/geo-info")
public class GeoSearchController {

    @Autowired
    private GeoInfoRepository geoInfoRepository;
    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @GetMapping()
    public Map<String, Object> getData() {
        GeoSearchEngineOperation operation = new GeoInfoFetcher(geoInfoRepository);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }

    @PostMapping("search")
    public @ResponseBody
    Map<String, Object> searchDataWithinDistance(@RequestBody GeopointResponseDto geopointResponseDto) throws Exception {
        GeoSearchEngineOperation operation = new GeoInfoRangeFetcher(geoInfoRepository, geoLocationRepository, geopointResponseDto);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }

    @GetMapping("stats")
    public Map<String, Object> nameStats(@RequestParam String name) {
        GeoSearchEngineOperation operation = new GeoInfoStatsFetcher(geoInfoRepository, name);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }

    @PutMapping("{id}")
    public @ResponseBody
    Map<String, Object> updateGeoPointForData(@PathVariable int id,
                                              @RequestBody GeoLocationResponseDto geoLocation) {
        GeoSearchEngineOperation operation = new GeoInfoUpdator(geoInfoRepository,
                geoLocationRepository,
                id,
                geoLocation);
        return GeoSearchResponseWrapper.INSTANCE.wrap(operation);
    }
}
