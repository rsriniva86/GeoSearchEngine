package com.shyam.GeoSearchEngine.core.geosearchengine.operations;

import com.shyam.GeoSearchEngine.core.geosearchengine.error.GeoSearchEngineException;
import com.shyam.GeoSearchEngine.core.geosearchengine.operations.GeoSearchEngineOperation;
import com.shyam.GeoSearchEngine.core.geosearchengine.utils.GeoSearchJSONHandler;
import com.shyam.GeoSearchEngine.models.db.DBGeoLocation;
import com.shyam.GeoSearchEngine.models.json.Geolocation;
import com.shyam.GeoSearchEngine.models.json.Geopoint;
import com.shyam.GeoSearchEngine.models.db.TestDataDB;
import com.shyam.GeoSearchEngine.models.json.TestData;
import com.shyam.GeoSearchEngine.repositories.GeoLocationRepository;
import com.shyam.GeoSearchEngine.repositories.TestDataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataUpdator implements GeoSearchEngineOperation {

    private final TestDataRepository testDataRepository;
    private final GeoLocationRepository geoLocationRepository;
    private final int id;
    private final Geolocation geoLocation;
    private final boolean isOverwrite;

    private final Logger logger= LogManager.getLogger(TestDataUpdator.class);
    public TestDataUpdator(TestDataRepository testDataRepository,
                           GeoLocationRepository geoLocationRepository,
                           int id,
                           Geolocation geoLocation,
                           boolean isOverwrite) {
        this.testDataRepository=testDataRepository;
        this.geoLocationRepository=geoLocationRepository;
        this.id=id;
        this.geoLocation=geoLocation;
        this.isOverwrite=isOverwrite;


    }

    @Override
    public Object doOperation() throws Exception {

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
        logger.info("Time Taken = " + timeTaken);

        Map<String, List<TestData>> data=
                GeoSearchJSONHandler
                        .INSTANCE
                        .groupByLocation(testDataPoints);

        Map<String,Object> updateRecordMap=new HashMap<>();
        updateRecordMap.put("isExistingLocation",isExistingLocation);
        updateRecordMap.put("isGeopointOverwritten",isGeopointOverwritten);
        updateRecordMap.put("data",data);

        return updateRecordMap;    }
}
