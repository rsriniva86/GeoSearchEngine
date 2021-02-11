package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.core.engine.utils.GeoSearchJSONHandler;
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

public class GeoInfoUpdator implements GeoSearchEngineOperation {

    private final GeoInfoRepository geoInfoRepository;
    private final GeoLocationRepository geoLocationRepository;
    private final int id;
    private final GeoLocationResponseDto geoLocation;
    private final boolean isOverwrite;

    private final Logger logger = LogManager.getLogger(GeoInfoUpdator.class);

    public GeoInfoUpdator(GeoInfoRepository geoInfoRepository,
                          GeoLocationRepository geoLocationRepository,
                          int id,
                          GeoLocationResponseDto geoLocation
    ) {
        this.geoInfoRepository = geoInfoRepository;
        this.geoLocationRepository = geoLocationRepository;
        this.id = id;
        this.geoLocation = geoLocation;
        this.isOverwrite = true;
    }


    @Override
    public Object doOperation() throws Exception {

        boolean isExistingLocation = false;
        boolean isGeopointOverwritten = false;

        long startTime = System.currentTimeMillis();
        GeopointResponseDto geoPoint = this.geoLocation.getGeopoint();
        GeoLocation geoLocation =
                geoLocationRepository.findByLocation(this.geoLocation.getLocation());
        if (geoLocation != null) {
            isExistingLocation = true;
            //If location already exists
            //Do overwrite with new value or keep to old values
            if (isOverwrite) {
                geoLocation.setLocation(this.geoLocation.getLocation());
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
                    this.geoLocation.getLocation(),
                    geoPoint.getLatitude(),
                    geoPoint.getLongitude());
            geoLocationRepository.save(geoLocation);
        }
        GeoInfo geoInfo = geoInfoRepository.findById(id);
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
