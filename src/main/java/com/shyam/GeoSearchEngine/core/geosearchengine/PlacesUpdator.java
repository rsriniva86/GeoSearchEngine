package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.models.db.DBGeoLocation;
import com.shyam.GeoSearchEngine.models.json.Geolocation;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.db.DBPlace;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlacesUpdator {
    public Map<String, List<TestData>> update(PlacesRepository placesRepository,
                          GeoLocationRepository geoLocationRepository,
                          int id,
                          Geolocation geoLocation,
                          boolean isOverwrite) {

        long startTime = System.currentTimeMillis();
        Geopoint geoPoint=geoLocation.getGeopoint();
        DBGeoLocation dbGeoLocation=
                geoLocationRepository.findByLocation(geoLocation.getLocation());
        if(dbGeoLocation!=null){
            //If location already exists
            //Do overwrite with new value or keep to old values
            if(isOverwrite) {
                dbGeoLocation.setLocation(geoLocation.getLocation());
                dbGeoLocation.setLatitude(geoPoint.getLatitude());
                dbGeoLocation.setLongitude(geoPoint.getLongitude());
                geoLocationRepository.save(dbGeoLocation);
            }else {
                //Do nothing
            }
        }else{
            //location does not exist in GeolocationDB
            dbGeoLocation
                    =new
                    DBGeoLocation(
                    geoLocation.getLocation(),
                    geoPoint.getLatitude(),
                    geoPoint.getLongitude());
            geoLocationRepository.save(dbGeoLocation);

        }
        DBPlace DBPlace = placesRepository.findById(id);
        DBPlace.setLocation_id(dbGeoLocation.getId());
        placesRepository.save(DBPlace);
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);

        List<DBPlace> places=new ArrayList<DBPlace>();
        places.add(DBPlace);

        return
                PlacesJSONHandler
                        .INSTANCE
                        .groupPlacesByLocation(places);

    }
}
