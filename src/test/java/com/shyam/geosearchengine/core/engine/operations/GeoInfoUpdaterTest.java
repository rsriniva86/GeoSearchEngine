package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineErrorCode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineException;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineMessages;
import com.shyam.geosearchengine.dto.GeoInfoResponseDto;
import com.shyam.geosearchengine.dto.GeoLocationResponseDto;
import com.shyam.geosearchengine.dto.GeopointResponseDto;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import com.shyam.geosearchengine.repositories.GeoLocationRepository;
import com.shyam.geosearchengine.testutils.TestDataSet;
import com.shyam.geosearchengine.testutils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

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
        inputGeolocation = new GeoLocationResponseDto(
                TestDataSet.getTestDataSets().get(0).getLocation(),
                geopointResponseDto);
        id = TestDataSet.getTestDataSets().get(0).getId();

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
            inputGeolocation.setGeopoint(new GeopointResponseDto(100, 100));
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
            inputGeolocation.setGeopoint(new GeopointResponseDto(-90.5, 100));
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
            inputGeolocation.setGeopoint(new GeopointResponseDto(1, 200));
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
            inputGeolocation.setGeopoint(new GeopointResponseDto(1, -181));
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

    @Test
    void test012UpdateWithExistingGeoLocation() {

        try {
            Mockito.when(geoInfoRepository.findById(id)).thenReturn(
                    Utils.createGeoInfoObject(TestDataSet.getTestDataSets().get(0)));
            Mockito.when(geoLocationRepository.findByLocation(
                    inputGeolocation.getLocation()))
                    .thenReturn
                            (Utils.createGeoLocationObject
                                    (TestDataSet.getTestDataSets().get(0))
            );
            inputGeolocation.getGeopoint().setLatitude(TestDataSet.getTestDataForUpdate().get(0).getLatitude());
            inputGeolocation.getGeopoint().setLongitude(TestDataSet.getTestDataForUpdate().get(0).getLongitude());
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            Map<String, Object> output = (Map<String, Object>) updater.doOperation();
            boolean isExistingLocation = (boolean) output.get("isExistingLocation");
            boolean isGeolocationOverwritten = (boolean) output.get("isGeolocationOverwritten");
            Map<String, List<GeoInfoResponseDto>> data = (Map<String, List<GeoInfoResponseDto>>) output.get("data");
            assertTrue(isExistingLocation);
            assertTrue(isGeolocationOverwritten);
            List<GeoInfoResponseDto> geoInfoResponseDtoList =
                    data.get(TestDataSet.getTestDataForUpdate().get(0).getLocation());
            assertEquals(1, geoInfoResponseDtoList.size());
            GeoInfoResponseDto geoInfoResponseDto = geoInfoResponseDtoList.get(0);
            assertEquals(
                    TestDataSet.getTestDataForUpdate().get(0).getLatitude(),
                    geoInfoResponseDto.getGeopoint().getLatitude());
            assertEquals(
                    TestDataSet.getTestDataForUpdate().get(0).getLongitude(),
                    geoInfoResponseDto.getGeopoint().getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }

    }
    @Test
    void test013UpdateWithNewGeoLocation() {

        try {
            Mockito.when(geoInfoRepository.findById(id)).thenReturn(
                    Utils.createGeoInfoObject(TestDataSet.getTestDataSets().get(0)));
            Mockito.when(geoLocationRepository.findByLocation(inputGeolocation.getLocation())).thenReturn(
                    Utils.createGeoLocationObject(TestDataSet.getTestDataSets().get(0))
            );
            inputGeolocation.setLocation(TestDataSet.getTestDataForUpdate().get(1).getLocation());
            inputGeolocation.getGeopoint().setLatitude(TestDataSet.getTestDataForUpdate().get(1).getLatitude());
            inputGeolocation.getGeopoint().setLongitude(TestDataSet.getTestDataForUpdate().get(1).getLongitude());
            GeoInfoUpdater updater = new GeoInfoUpdater(
                    geoInfoRepository,
                    geoLocationRepository,
                    id,
                    inputGeolocation);
            Map<String, Object> output = (Map<String, Object>) updater.doOperation();
            boolean isExistingLocation = (boolean) output.get("isExistingLocation");
            boolean isGeolocationOverwritten = (boolean) output.get("isGeolocationOverwritten");
            Map<String, List<GeoInfoResponseDto>> data =
                    (Map<String, List<GeoInfoResponseDto>>) output.get("data");
            assertFalse(isExistingLocation);
            assertFalse(isGeolocationOverwritten);
            List<GeoInfoResponseDto> geoInfoResponseDtoList =
                    data.get(TestDataSet.getTestDataForUpdate().get(1).getLocation());
            assertEquals(1, geoInfoResponseDtoList.size());
            GeoInfoResponseDto geoInfoResponseDto = geoInfoResponseDtoList.get(0);
            assertEquals(
                    TestDataSet.getTestDataForUpdate().get(1).getLatitude(),
                    geoInfoResponseDto.getGeopoint().getLatitude()
            );
            assertEquals(
                    TestDataSet.getTestDataForUpdate().get(1).getLongitude(),
                    geoInfoResponseDto.getGeopoint().getLongitude()
            );

        } catch (Exception e) {
            e.printStackTrace();
            fail("exception is NOT expected");
        }
    }
}