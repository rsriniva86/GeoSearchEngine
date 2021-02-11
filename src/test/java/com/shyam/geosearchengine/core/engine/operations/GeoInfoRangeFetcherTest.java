package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.core.AppConfiguration;
import com.shyam.geosearchengine.dto.GeoInfoResponseDto;
import com.shyam.geosearchengine.dto.GeopointResponseDto;
import com.shyam.geosearchengine.models.GeoInfo;
import com.shyam.geosearchengine.models.GeoLocation;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import com.shyam.geosearchengine.repositories.GeoLocationRepository;
import com.shyam.geosearchengine.testutils.TestDataSet;
import com.shyam.geosearchengine.testutils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.shyam.geosearchengine.testutils.Utils.createGeoInfoObject;
import static com.shyam.geosearchengine.testutils.Utils.testGeoInfoResponseDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeoInfoRangeFetcherTest {

    private GeoInfoRepository geoInfoRepository;
    private GeoLocationRepository geoLocationRepository;
    private GeopointResponseDto inputGeopoint;

    @BeforeEach
    void setUp() {
        geoInfoRepository = mock(GeoInfoRepository.class);
        geoLocationRepository= mock(GeoLocationRepository.class);
        inputGeopoint =new GeopointResponseDto(
                TestDataSet.getTestDataSets().get(0).getLatitude(),
                TestDataSet.getTestDataSets().get(0).getLongitude()
                );

    }
    @Test
    void test001_NoLocationWithinRange() {

        List<GeoLocation> geoLocations=new ArrayList<>();
        when(geoLocationRepository.findWithinXKms(
                inputGeopoint.getLatitude()
                , inputGeopoint.getLongitude()
                , AppConfiguration.DISTANCE_RANGE))
                .thenReturn(
                        geoLocations
                );
        List<Long> locationIDList = StreamSupport
                .stream(geoLocations.spliterator(), false)
                .map(GeoLocation::getId)
                .collect(Collectors.toList());
        when(geoInfoRepository.findWithinXKms(locationIDList)).thenReturn(
                new ArrayList<>()
        );
        try {
            GeoInfoRangeFetcher fetcher = new GeoInfoRangeFetcher(
                    geoInfoRepository,
                    geoLocationRepository,
                    inputGeopoint);
            Object object=fetcher.doOperation();
            assertTrue(object instanceof Map,"Object is not a map");
            Map<String, List<GeoInfoResponseDto>> data= (Map<String, List<GeoInfoResponseDto>>) object;
            assertTrue(data.size()==0,"Size is not zero");
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }
    @Test
    void test002_OneLocationWithInRange() {
        List<GeoLocation> geoLocations=new ArrayList<>();
        geoLocations.add(Utils.createGeoLocationObject(TestDataSet.getTestDataSets().get(0)));

        when(geoLocationRepository.findWithinXKms(
                inputGeopoint.getLatitude()
                , inputGeopoint.getLongitude()
                , AppConfiguration.DISTANCE_RANGE))
                .thenReturn(
                        geoLocations
                );
        List<Long> locationIDList = StreamSupport
                .stream(geoLocations.spliterator(), false)
                .map(GeoLocation::getId)
                .collect(Collectors.toList());

        List<GeoInfo> geoInfos=new ArrayList<>();
        geoInfos.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(0)));

        when(geoInfoRepository.findWithinXKms(locationIDList)).thenReturn(
                geoInfos
        );
        try {
            GeoInfoRangeFetcher fetcher = new GeoInfoRangeFetcher(
                    geoInfoRepository,
                    geoLocationRepository,
                    inputGeopoint);
            Object object=fetcher.doOperation();
            assertTrue(object instanceof Map,"Object is not a map");
            Map<String,List<GeoInfoResponseDto>> data= (Map<String, List<GeoInfoResponseDto>>) object;
            assertEquals(1,data.size(),"Size is not one");
            assertNotNull(data.get(TestDataSet.getTestDataSets().get(0).getLocation()),"List is null");
            List<GeoInfoResponseDto> geoInfoResponseDtoList1 =
                    data.get(
                            TestDataSet.getTestDataSets().get(0).getLocation());
            assertTrue(geoInfoResponseDtoList1.size()==1,"Size of TestDataList is not one");
            testGeoInfoResponseDto(geoInfoResponseDtoList1.get(0), TestDataSet.getTestDataSets().get(0));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception not expected");
        }

    }
    @Test
    void test003_MultipleLocationsWithInRange() {
        List<GeoLocation> geoLocations=new ArrayList<>();
        geoLocations.add(Utils.createGeoLocationObject(TestDataSet.getTestDataSets().get(0)));

        when(geoLocationRepository.findWithinXKms(
                inputGeopoint.getLatitude()
                , inputGeopoint.getLongitude()
                , AppConfiguration.DISTANCE_RANGE))
                .thenReturn(
                        geoLocations
                );
        List<Long> locationIDList = StreamSupport
                .stream(geoLocations.spliterator(), false)
                .map(GeoLocation::getId)
                .collect(Collectors.toList());

        List<GeoInfo> geoInfos=new ArrayList<>();
        geoInfos.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(0)));
        geoInfos.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(1)));
        geoInfos.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(3)));
        geoInfos.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(4)));
        when(geoInfoRepository.findWithinXKms(locationIDList)).thenReturn(
                geoInfos
        );
        try {
            GeoInfoRangeFetcher fetcher = new GeoInfoRangeFetcher(
                    geoInfoRepository,
                    geoLocationRepository,
                    inputGeopoint);
            Object object=fetcher.doOperation();
            assertTrue(object instanceof Map,"Object is not a map");
            Map<String,List<GeoInfoResponseDto>> data= (Map<String, List<GeoInfoResponseDto>>) object;
            assertNotEquals(0,data.size(),"Size is zero");
            for(int index=0;index<data.size();index++) {
                assertNotNull(data.get(
                        TestDataSet.
                                getTestDataSets().
                                get(index).
                                getLocation()),
                        "List is null for index" + index);
                List<GeoInfoResponseDto> geoInfoResponseDtoList =
                        data.get(
                                TestDataSet.getTestDataSets().get(index).getLocation());
                assertTrue(geoInfoResponseDtoList.size()>0,
                        "Size of TestDataList is zero for index"+index);
                for (GeoInfoResponseDto geoInfoResponseDto: geoInfoResponseDtoList) {
                    int indexForDataSet= (int) (geoInfoResponseDto.getId()-1);
                    testGeoInfoResponseDto(
                            geoInfoResponseDto,
                            TestDataSet
                                    .getTestDataSets()
                                    .get(indexForDataSet)
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception not expected");
        }

    }

}