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

        if (null == geoInfoRepository
                || null == geoLocationRepository) {
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.REPOSITORY_NOT_AVAILABLE,
                    GeoSearchEngineMessages.REPOSITORY_NOT_AVAILABLE);
        }
        else if(null== inputGeoLocation
                || null== inputGeoLocation.getLocation()
                || inputGeoLocation.getLocation().isEmpty()
                || null== inputGeoLocation.getGeopoint()
        ){
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    GeoSearchEngineMessages.INVALID_INPUT);
        }else if(!LatitudeLongitudeValidator
                .INSTANCE
                .isValidLatitude(inputGeoLocation.getGeopoint().getLatitude())){
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    GeoSearchEngineMessages.INVALID_INPUT_LATITUDE);
        }else if(!LatitudeLongitudeValidator
                .INSTANCE
                .isValidLongitude(inputGeoLocation.getGeopoint().getLongitude())){
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    GeoSearchEngineMessages.INVALID_INPUT_LONGITUDE);
        }

        GeoInfo geoInfo = geoInfoRepository.findById(id);
        if(geoInfo==null){
            throw new GeoSearchEngineException(
                    GeoSearchEngineErrorCode.DATA_NOT_EXISTS,
                    GeoSearchEngineMessages.DATA_NOT_EXISTS);
        }

        boolean isExistingLocation = false;
        boolean isGeopointOverwritten = false;

        long startTime = System.currentTimeMillis();
        GeopointResponseDto geoPoint = this.inputGeoLocation.getGeopoint();
        GeoLocation geoLocation =
                geoLocationRepository.findByLocation(this.inputGeoLocation.getLocation());
        if (geoLocation != null) {
            isExistingLocation = true;
            //If location already exists
            //Do overwrite with new value or keep to old values
            if (isOverwrite) {
                geoLocation.setLocation(this.inputGeoLocation.getLocation());
                geoLocation.setLatitude(geoPoint.getLatitude());
                geoLocation.setLongitude(geoPoint.getLongitude());
                geoLocationRepository.save(geoLocation);
                isGeopointOverwritten = true;
            } else {
                //Do nothing
                isGeopointOverwritten = false;
            }
        } else {
            //location does not exist in GeolocationDB
            isExistingLocation = false;
            geoLocation
                    = new
                    GeoLocation(
                    this.inputGeoLocation.getLocation(),
                    geoPoint.getLatitude(),
                    geoPoint.getLongitude());
            geoLocationRepository.save(geoLocation);
        }

        geoInfo.setGeoLocation(geoLocation);
        geoInfoRepository.save(geoInfo);
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
        updateRecordMap.put("isGeopointOverwritten", isGeopointOverwritten);
        updateRecordMap.put("data", data);
        return updateRecordMap;
    }
}
