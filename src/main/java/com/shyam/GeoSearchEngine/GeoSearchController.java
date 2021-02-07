package com.shyam.GeoSearchEngine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.models.GeoPoint;
import com.shyam.GeoSearchEngine.models.Place;
import com.shyam.GeoSearchEngine.models.StringResponse;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping("/geoSearch")
public class GeoSearchController {

    @Autowired
    private PlacesRepository placesRepository;

    @GetMapping("places")
    public String getPlaces() throws Exception{
        Long startTime=System.currentTimeMillis();
        Iterable<Place> places = placesRepository.findAll();
        long timeTaken=System.currentTimeMillis()-startTime;
        System.out.println("Time Taken = "+timeTaken );
//      System.out.println("Places found with find all");
//      System.out.println("-------------------------------");
//      for (Place place : places) {
//           System.out.println(place.toString());
//      }
//      System.out.println("");
        Map<String, List<Place>> placesByLocation=groupPlacesByLocation(places);
        String jsonOutput=convertPlacesByLocationToJSON(placesByLocation);
        //System.out.println("Places JSONGroupedByLocation\n" + jsonOutput);
        return jsonOutput;
    }

    @PostMapping("places/search")
    public @ResponseBody StringResponse searchPlacesWithinDistance(@RequestBody GeoPoint geoPoint) throws Exception{

        Long startTime=System.currentTimeMillis();
        System.out.println(" Latitude::"+geoPoint.getLatitude() );
        System.out.println("Longitude::"+geoPoint.getLongitude() );
        Iterable<Place> places = placesRepository.findWithinXKms(
                geoPoint.getLatitude(),
                geoPoint.getLongitude(),
                AppConfiguration.DISTANCE_RANGE
        );
        long timeTaken=System.currentTimeMillis()-startTime;
        System.out.println("Time Taken = "+timeTaken );
        Map<String, List<Place>> placesByLocation=groupPlacesByLocation(places);
        String jsonOutput = convertPlacesByLocationToJSON(placesByLocation);
        //System.out.println("Places JSONGroupedByLocation\n" + jsonOutput);
        return new StringResponse(jsonOutput);
    }

    @GetMapping("places/stats")
    public String nameStats(@RequestParam String name) throws Exception{
        Long startTime=System.currentTimeMillis();
        int exactNameMatch = placesRepository.findExactNameMatch(name);
        int partialNameMatch=placesRepository.findPartialNameMatch(name);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("exactMatchCount", exactNameMatch);
        rootNode.put("matchCount", partialNameMatch);
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        long timeTaken=System.currentTimeMillis()-startTime;
        System.out.println("Time Taken = "+timeTaken );
        return jsonString;
    }

    @PutMapping("/places/{id}")
    public @ResponseBody Place updateGeoPointForPlaces(@PathVariable int id,
                                              @RequestBody GeoPoint geoPoint) throws Exception{

        Long startTime=System.currentTimeMillis();
        System.out.println("id::"+id);
        System.out.println(" Latitude::"+geoPoint.getLatitude() );
        System.out.println("Longitude::"+geoPoint.getLongitude() );

        Place place = placesRepository.findById(1);
        place.setLatitude(geoPoint.getLatitude());
        place.setLongitude(geoPoint.getLongitude());
        placesRepository.save(place);
        boolean isUpdated= place.getLatitude()!=1.3;
        System.out.println("isUpdated? "+isUpdated );
        long timeTaken=System.currentTimeMillis()-startTime;
        System.out.println("Time Taken = "+timeTaken );
        return place;
    }


    private String convertPlacesByLocationToJSON(Map<String, List<Place>> placesByLocation) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(placesByLocation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public  Map<String, List<Place>> groupPlacesByLocation(Iterable<Place> places) {
        List<Place> placeList= StreamSupport
                .stream(places.spliterator(),false)
                .collect(Collectors.toList());
        final Map<String, List<Place>> placesByLocation =
                placeList
                        .stream()
                        .collect(
                                groupingBy(Place::getLocation)
                        );

        return placesByLocation;
    }
}
