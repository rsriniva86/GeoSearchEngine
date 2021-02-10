package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.core.AppConfiguration;
import com.shyam.GeoSearchEngine.models.json.GeoLocation;
import com.shyam.GeoSearchEngine.models.json.GeoPoint;
import com.shyam.GeoSearchEngine.models.db.Place;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.PlacesRepository;

public class PlacesUpdator {
    public Place update(PlacesRepository placesRepository,
                        GeoLocationRepository geoLocationRepository,
                        int id,
                        GeoLocation geoLocation,
                        boolean isOverwrite) {

        long startTime = System.currentTimeMillis();
        GeoPoint geoPoint=geoLocation.getGeopoint();
        System.out.println("id::" + id);
        System.out.println("location::" + geoLocation.getLocation());
        System.out.println("Latitude::" + geoPoint.getLatitude());
        System.out.println("Longitude::" + geoPoint.getLongitude());

        com.shyam.GeoSearchEngine.models.db.GeoLocation geoLocationDB=
                geoLocationRepository.findByLocation(geoLocation.getLocation());
        if(geoLocationDB!=null){
            //If location already exists
            //Do overwrite with new value or keep to old values
            if(isOverwrite) {
                geoLocationDB.setLocation(geoLocation.getLocation());
                geoLocationDB.setLatitude(geoPoint.getLatitude());
                geoLocationDB.setLongitude(geoPoint.getLongitude());
                geoLocationRepository.save(geoLocationDB);
            }else {
                //Do nothing
            }
        }else{
            //location does not exist in GeolocationDB
            geoLocationDB
                    =new
                    com.shyam.GeoSearchEngine.models.db.GeoLocation(
                    geoLocation.getLocation(),
                    geoPoint.getLatitude(),
                    geoPoint.getLongitude());
            geoLocationRepository.save(geoLocationDB);

        }
        Place place = placesRepository.findById(id);
        place.setLocation_id(geoLocationDB.getId());
        placesRepository.save(place);
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);
        return place;
    }
}
