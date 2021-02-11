package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineErrorCode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineException;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineMessages;
import com.shyam.geosearchengine.dto.GeoLocationResponseDto;
import com.shyam.geosearchengine.dto.GeopointResponseDto;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import com.shyam.geosearchengine.repositories.GeoLocationRepository;
import com.shyam.geosearchengine.testutils.TestDataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GeoInfoUpdaterTest {

    private GeoInfoRepository geoInfoRepository;
    private GeoLocationRepository geoLocationRepository;
    private GeoLocationResponseDto inputGeolocation;
    private long id;

    @BeforeEach
    void setUp() {
        geoInfoRepository = mock(GeoInfoRepository.class);
        geoLocationRepository = mock(GeoLocationRepository.class);

        GeopointResponseDto geopointResponseDto = new GeopointResponseDto(
                TestDataSet.getTestDataSets().get(0).getLatitude(),
                TestDataSet.getTestDataSets().get(0).getLongitude()
        );
        inputGeolocation=new GeoLocationResponseDto(
                TestDataSet.getTestDataSets().get(0).getLocation(),
                geopointResponseDto);
        id=TestDataSet.getTestDataSets().get(0).getId();

    }

    @Test
    void test001GeoInfoRepositoryNull() {
        try {
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    null,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.REPOSITORY_NOT_AVAILABLE,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.REPOSITORY_NOT_AVAILABLE,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }

    @Test
    void test002GeoLocationRepositoryNull() {

        try {
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    null,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.REPOSITORY_NOT_AVAILABLE,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.REPOSITORY_NOT_AVAILABLE,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }

    @Test
    void test003GeoLocationNull() {

        try {
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    null);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.INVALID_INPUT,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }
    @Test
    void test004LocationNull() {

        try {
            inputGeolocation.setLocation(null);
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.INVALID_INPUT,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }
    @Test
    void test005LocationEmpty() {

        try {
            inputGeolocation.setLocation("");
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.INVALID_INPUT,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }
    @Test
    void test006GeoPointNull() {

        try {
            inputGeolocation.setGeopoint(null);
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.INVALID_INPUT,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }
    @Test
    void test007GeoPointInvalidLatitudeMaxRangeExceeded() {
        try {
            inputGeolocation.setGeopoint(new GeopointResponseDto(100,100));
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.INVALID_INPUT_LATITUDE,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }

    @Test
    void test008GeoPointInvalidLatitudeMinRangeExceeded() {

        try {
            inputGeolocation.setGeopoint(new GeopointResponseDto(-90.5,100));
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.INVALID_INPUT_LATITUDE,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }

    @Test
    void test009GeoPointInvalidLongitudeMaxRangeExceeded() {

        try {
            inputGeolocation.setGeopoint(new GeopointResponseDto(1,200));
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.INVALID_INPUT_LONGITUDE,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }

    @Test
    void test010GeoPointInvalidLongitudeMinRangeExceeded() {

        try {
            inputGeolocation.setGeopoint(new GeopointResponseDto(1,-181));
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.INVALID_INPUT,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.INVALID_INPUT_LONGITUDE,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }

    @Test
    void test011IDNotExists() {

        try {
            Mockito.when(geoInfoRepository.findById(id)).thenReturn(null);
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            updater.doOperation();
        } catch (GeoSearchEngineException geoSearchEngineException) {
            assertEquals(
                    GeoSearchEngineErrorCode.DATA_NOT_EXISTS,
                    geoSearchEngineException.getCode(), "Error code is not as expected");
            assertEquals(
                    GeoSearchEngineMessages.DATA_NOT_EXISTS,
                    geoSearchEngineException.getMessage(),
                    "message is not as expected");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
        fail("exception is expected");
    }
}