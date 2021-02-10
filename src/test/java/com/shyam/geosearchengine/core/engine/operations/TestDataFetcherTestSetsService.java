package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.TestDataSets;
import com.shyam.geosearchengine.models.GeoLocation;
import com.shyam.geosearchengine.models.GeoInfo;
import com.shyam.geosearchengine.dto.GeoInfoResponseDto;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestDataFetcherTestSetsService {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test001_OperationForEmptyTable() {
        GeoInfoRepository geoInfoRepository = mock(GeoInfoRepository.class);
        when(geoInfoRepository.findAll()).thenReturn(
          new ArrayList<GeoInfo>()
        );
        GeoInfoFetcher fetcher = new GeoInfoFetcher(geoInfoRepository);
        try {
            Object object=fetcher.doOperation();
            assertTrue(object instanceof Map,"Object is not a map");
            Map<String,List<GeoInfoResponseDto>> data= (Map<String, List<GeoInfoResponseDto>>) object;
            assertTrue(data.size()==0,"Size is not zero");
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }
    @Test
    void test002_OperationForSingleEntry() {
        GeoInfoRepository geoInfoRepository = mock(GeoInfoRepository.class);
        final Iterable<GeoInfo> testDataList=new Iterable<GeoInfo>() {
            @Override
            public Iterator<GeoInfo> iterator() {
                List<GeoInfo> list=new ArrayList<>();
                list.add(createTestDBObject1(TestDataSets.getTestDataSets().get(0)));
                return list.iterator();
            }
        };
        when(geoInfoRepository.findAll()).thenReturn(testDataList);

        GeoInfoFetcher fetcher = new GeoInfoFetcher(geoInfoRepository);
        try {
            Object object=fetcher.doOperation();
            assertTrue(object instanceof Map,"Object is not a map");
            Map<String,List<GeoInfoResponseDto>> data= (Map<String, List<GeoInfoResponseDto>>) object;
            assertTrue(data.size()==1,"Size is not one");
            assertNotNull(data.get(TestDataSets.getTestDataSets().get(0).getLOCATION()),"List is null");
            List<GeoInfoResponseDto> geoInfoResponseDtoList1 =data.get("21CNR");
            assertTrue(geoInfoResponseDtoList1.size()==1,"Size of TestDataList is not one");
            testTestDataObject1(geoInfoResponseDtoList1.get(0),TestDataSets.getTestDataSets().get(0));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception not expected");
        }

    }

    private GeoInfo createTestDBObject1(TestDataSets dataset) {
        GeoInfo geoInfo =new GeoInfo();
        geoInfo.setId(dataset.getID());
        geoInfo.setName(dataset.getNAME());
        geoInfo.setLocation_id(dataset.getLOCATION_ID());
        geoInfo.setGeoLocation(new GeoLocation());
        geoInfo.getGeoLocation().setLocation(dataset.getLOCATION());
        geoInfo.getGeoLocation().setLatitude(dataset.getLATITUDE());
        geoInfo.getGeoLocation().setLongitude(dataset.getLONGITUDE());
        return geoInfo;
    }

    private void testTestDataObject1(GeoInfoResponseDto geoInfoResponseDto, TestDataSets dataSet) {
        assertTrue(geoInfoResponseDto.getId()==dataSet.getID(),"ID is wrong");
        assertTrue(geoInfoResponseDto.getName().equalsIgnoreCase(dataSet.getNAME()),"Name is wrong");
        assertTrue(geoInfoResponseDto.getLocation().equalsIgnoreCase(dataSet.getLOCATION()),"Location is wrong");
        assertTrue(geoInfoResponseDto.getGeopoint().getLatitude()== dataSet.getLATITUDE(),"Latitude is wrong");
        assertTrue(geoInfoResponseDto.getGeopoint().getLongitude()==dataSet.getLONGITUDE(),"Longitude is wrong");
    }

}