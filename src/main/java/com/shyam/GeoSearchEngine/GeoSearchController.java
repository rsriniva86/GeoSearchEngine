package com.shyam.GeoSearchEngine;

import com.shyam.GeoSearchEngine.core.geosearchengine.PlacesFetcher;
import com.shyam.GeoSearchEngine.core.geosearchengine.PlacesStatsFetcher;
import com.shyam.GeoSearchEngine.core.geosearchengine.PlacesUpdator;
import com.shyam.GeoSearchEngine.models.GeoPoint;
import com.shyam.GeoSearchEngine.models.Place;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geoSearch")
public class GeoSearchController {

    @Autowired
    private PlacesRepository placesRepository;
    @Autowired
    private GeoLocationRepository geoLocationRepository;

    @GetMapping("places")
    public String getPlaces() throws Exception {
        PlacesFetcher fetcher = new PlacesFetcher();
        return fetcher.get(placesRepository);
    }

    @PostMapping("places/search")
    public @ResponseBody String searchPlacesWithinDistance(@RequestBody GeoPoint geoPoint) throws Exception {
        PlacesFetcher fetcher = new PlacesFetcher();
        return fetcher.get(placesRepository, geoLocationRepository,geoPoint);
    }

    @GetMapping("places/stats")
    public String nameStats(@RequestParam String name) throws Exception {
        PlacesStatsFetcher fetcher = new PlacesStatsFetcher();
        return fetcher.get(placesRepository, name);
    }

    @PutMapping("/places/{id}")
    public @ResponseBody
    Place updateGeoPointForPlaces(@PathVariable int id,
                                  @RequestBody GeoPoint geoPoint) throws Exception {
        PlacesUpdator placesUpdator = new PlacesUpdator();
        return placesUpdator.update(placesRepository, id, geoPoint);
    }

}
