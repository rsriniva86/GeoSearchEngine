package com.shyam.geosearchengine.core.engine.operations;

import com.shyam.geosearchengine.testutils.TestDataSet;
import com.shyam.geosearchengine.models.GeoInfo;
import com.shyam.geosearchengine.dto.GeoInfoResponseDto;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.shyam.geosearchengine.testutils.Utils.createGeoInfoObject;
import static com.shyam.geosearchengine.testutils.Utils.testGeoInfoResponseDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeoInfoFetcherTest {

    private GeoInfoRepository geoInfoRepository;

    @BeforeEach
    void setUp() {
        geoInfoRepository = mock(GeoInfoRepository.class);
    }



    @Test
    void test001_OperationForEmptyTable() {
        when(geoInfoRepository.findAll()).thenReturn(
          new ArrayList<>()
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
        final Iterable<GeoInfo> testDataList= () -> {
            List<GeoInfo> list=new ArrayList<>();
            list.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(0)));
            return list.iterator();
        };
        when(geoInfoRepository.findAll()).thenReturn(testDataList);

        GeoInfoFetcher fetcher = new GeoInfoFetcher(geoInfoRepository);
        try {
            Object object=fetcher.doOperation();
            assertTrue(object instanceof Map,"Object is not a map");
            Map<String,List<GeoInfoResponseDto>> data= (Map<String, List<GeoInfoResponseDto>>) object;
            assertTrue(data.size()==1,"Size is not one");
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
    void test003_OperationForMultipleEntries() {
        final Iterable<GeoInfo> testDataList= () -> {
            List<GeoInfo> list=new ArrayList<>();
            list.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(0)));
            list.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(1)));
            list.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(2)));
            list.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(3)));
            list.add(createGeoInfoObject(TestDataSet.getTestDataSets().get(4)));
            return list.iterator();
        };
        when(geoInfoRepository.findAll()).thenReturn(testDataList);

        GeoInfoFetcher fetcher = new GeoInfoFetcher(geoInfoRepository);
        try {
            Object object=fetcher.doOperation();
            assertTrue(object instanceof Map,"Object is not a map");
            Map<String,List<GeoInfoResponseDto>> data= (Map<String, List<GeoInfoResponseDto>>) object;
            assertTrue(data.size()>0,"Size is zero");
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