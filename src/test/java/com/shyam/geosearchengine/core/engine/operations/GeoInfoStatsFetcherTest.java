package com.shyam.geosearchengine.core.engine.operations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import com.shyam.geosearchengine.testutils.TestDataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeoInfoStatsFetcherTest {
    private GeoInfoRepository geoInfoRepository;
    private String name;

    @BeforeEach
    void setUp() {
        geoInfoRepository = mock(GeoInfoRepository.class);
        name= TestDataSet.getTestDataSets().get(0).getName();
    }
    @Test
    void test001EmptyTable() {
        when(geoInfoRepository.findAll()).thenReturn(
                new ArrayList<>()
        );
        GeoInfoStatsFetcher fetcher = new GeoInfoStatsFetcher(geoInfoRepository,name);
        try {
            Object object=fetcher.doOperation();
            assertNotNull(object,"output is null");
            assertTrue(object instanceof ObjectNode,"Object is not a ObjectNode");
            ObjectNode data= (ObjectNode) object;
            int exactMatchCount=data.get("exactMatchCount").asInt();
            int matchCount=data.get("matchCount").asInt();
            assertEquals(0,exactMatchCount);
            assertEquals(0,matchCount);
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }
}