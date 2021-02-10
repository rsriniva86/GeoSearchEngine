package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.models.db.DBGeoLocation;
import com.shyam.GeoSearchEngine.models.json.Geolocation;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.db.TestDataDB;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataUpdator {
    public Map<String, Object> update(TestDataRepository testDataRepository,
                                              GeoLocationRepository geoLocationRepository,
                                              int id,
                                              Geolocation geoLocation,
                                              boolean isOverwrite) throws GeoSearchEngineException{

        boolean isExistingLocation=false;
        boolean isGeopointOverwritten=false;

        long startTime = System.currentTimeMillis();
        Geopoint geoPoint=geoLocation.getGeopoint();
        DBGeoLocation dbGeoLocation=
                geoLocationRepository.findByLocation(geoLocation.getLocation());
        if(dbGeoLocation!=null){
            isExistingLocation=true;
            //If location already exists
            //Do overwrite with new value or keep to old values
            if(isOverwrite) {
                dbGeoLocation.setLocation(geoLocation.getLocation());
                dbGeoLocation.setLatitude(geoPoint.getLatitude());
                dbGeoLocation.setLongitude(geoPoint.getLongitude());
                geoLocationRepository.save(dbGeoLocation);
                isGeopointOverwritten=true;
            }else {
                //Do nothing
                isGeopointOverwritten=false;
            }
        }else{
            //location does not exist in GeolocationDB
            isExistingLocation=false;
            dbGeoLocation
                    =new
                    DBGeoLocation(
                    geoLocation.getLocation(),
                    geoPoint.getLatitude(),
                    geoPoint.getLongitude());
            geoLocationRepository.save(dbGeoLocation);
        }
        TestDataDB testDataDB = testDataRepository.findById(id);
        testDataDB.setLocation_id(dbGeoLocation.getId());
        testDataDB.setGeoLocation(dbGeoLocation);
        testDataRepository.save(testDataDB);
        List<TestDataDB> testDataPoints=new ArrayList<>();
        testDataPoints.add(testDataDB);
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken = " + timeTaken);

        Map<String, List<TestData>> data=
                GeoSearchJSONHandler
                        .INSTANCE
                        .groupByLocation(testDataPoints);

        Map<String,Object> updateRecordMap=new HashMap<>();
        updateRecordMap.put("isExistingLocation",isExistingLocation);
        updateRecordMap.put("isGeopointOverwritten",isGeopointOverwritten);
        updateRecordMap.put("data",data);

        return updateRecordMap;

    }
}
