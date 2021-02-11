package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineErrorCode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineException;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineMessages;
import com.shyam.geosearchengine.core.engine.utils.GeoSearchJSONHandler;
import com.shyam.geosearchengine.core.engine.utils.LatitudeLongitudeValidator;
import com.shyam.geosearchengine.dto.GeoInfoResponseDto;
import com.shyam.geosearchengine.dto.GeoLocationResponseDto;
import com.shyam.geosearchengine.dto.GeopointResponseDto;
import com.shyam.geosearchengine.models.GeoInfo;
import com.shyam.geosearchengine.models.GeoLocation;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import com.shyam.geosearchengine.repositories.GeoLocationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoInfoUpdater implements GeoSearchEngineOperation {

    private final GeoInfoRepository geoInfoRepository;
    private final GeoLocationRepository geoLocationRepository;
    private final long id;
    private final GeoLocationResponseDto inputGeoLocation;
    private final boolean isOverwrite;

    private final Logger logger = LogManager.getLogger(GeoInfoUpdater.class);

    public GeoInfoUpdater(GeoInfoRepository geoInfoRepository,
                          GeoLocationRepository geoLocationRepository,
                          long id,
                          GeoLocationResponseDto inputGeoLocation
    ) {
        this.geoInfoRepository = geoInfoRepository;
        this.geoLocationRepository = geoLocationRepository;
        this.id = id;
        this.inputGeoLocation = inputGeoLocation;
        this.isOverwrite = true;
    }


    @Override
    public Object doOperation() throws Exception {

        logger.info("Start validations");
        if (null == geoInfoRepository
                || null == geoLocationRepository) {
            logger.error(GeoSearchEngineMessages.REPOSITORY_NOT_AVAILABLE);
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.REPOSITORY_NOT_AVAILABLE,
                    GeoSearchEngineMessages.REPOSITORY_NOT_AVAILABLE);
        }
        else if(null== inputGeoLocation
                || null== inputGeoLocation.getLocation()
                || inputGeoLocation.getLocation().isEmpty()
                || null== inputGeoLocation.getGeopoint()
        ){
            logger.error(GeoSearchEngineMessages.INVALID_INPUT);
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    GeoSearchEngineMessages.INVALID_INPUT);
        }else if(!LatitudeLongitudeValidator
                .INSTANCE
                .isValidLatitude(inputGeoLocation.getGeopoint().getLatitude())){
            logger.error(GeoSearchEngineMessages.INVALID_INPUT_LATITUDE);
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    GeoSearchEngineMessages.INVALID_INPUT_LATITUDE);
        }else if(!LatitudeLongitudeValidator
                .INSTANCE
                .isValidLongitude(inputGeoLocation.getGeopoint().getLongitude())){
            logger.error(GeoSearchEngineMessages.INVALID_INPUT_LONGITUDE);

            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    GeoSearchEngineMessages.INVALID_INPUT_LONGITUDE);
        }
        logger.info("End validations");
        logger.info("Get geo info for ID"+id);
        GeoInfo geoInfo = geoInfoRepository.findById(id);
        if(geoInfo==null){
            logger.error("geo info is null");
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.DATA_NOT_EXISTS,
                    GeoSearchEngineMessages.DATA_NOT_EXISTS);
        }else{
            logger.info("Geoinfo is not null");
        }

        boolean isExistingLocation = false;
        boolean isGeolocationOverwritten = false;

        long startTime = System.currentTimeMillis();
        logger.info("getGeoLocation for "+this.inputGeoLocation.getLocation());
        GeoLocation geoLocation =
                geoLocationRepository.findByLocation(this.inputGeoLocation.getLocation());
        if (geoLocation != null) {
            logger.info("This is existing location");
            isExistingLocation = true;
            //If location already exists
            //Do overwrite with new value or keep to old values
            //Take note that the overwrite will not only change the current GeoInfo
            // but all GeoInfo records with the same GeoLocation
            if (isOverwrite) {
                logger.info("current policy is overwrite when GeoLocation already exists");
                geoLocation.setLocation(this.inputGeoLocation.getLocation());
                geoLocation.setLatitude(this.inputGeoLocation.getGeopoint().getLatitude());
                geoLocation.setLongitude(this.inputGeoLocation.getGeopoint().getLongitude());
                geoLocationRepository.save(geoLocation);
                logger.info("Geolocation updated to db"+geoLocation.toString());
                isGeolocationOverwritten = true;
            } else {
                //Do nothing
                isGeolocationOverwritten = false;
                logger.info("current policy is NOT overwrite when GeoLocation already exists");
                logger.info("Geolocation in db"+geoLocation.toString());
            }
        } else {
            //location does not exist in GeolocationDB
            isExistingLocation = false;
            logger.info("This is new location");
            geoLocation
                    = new
                    GeoLocation(
                    this.inputGeoLocation.getLocation(),
                    this.inputGeoLocation.getGeopoint().getLatitude(),
                    this.inputGeoLocation.getGeopoint().getLongitude());
            geoLocationRepository.save(geoLocation);
            logger.info("New Location Saved");
        }

        geoInfo.setGeoLocation(geoLocation);
        geoInfoRepository.save(geoInfo);
        logger.info("GeoInfo updated:"+geoInfo.toString());
        List<GeoInfo> geoInfos = new ArrayList<>();
        geoInfos.add(geoInfo);
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken = " + timeTaken);

        Map<String, List<GeoInfoResponseDto>> data =
                GeoSearchJSONHandler
                        .INSTANCE
                        .groupByLocation(geoInfos);

        Map<String, Object> updateRecordMap = new HashMap<>();
        updateRecordMap.put("isExistingLocation", isExistingLocation);
        updateRecordMap.put("isGeolocationOverwritten", isGeolocationOverwritten);
        updateRecordMap.put("data", data);
        logger.info(updateRecordMap);
        return updateRecordMap;
    }
}
