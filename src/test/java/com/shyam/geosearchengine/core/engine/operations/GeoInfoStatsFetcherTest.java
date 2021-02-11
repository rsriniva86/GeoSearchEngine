package com.shyam.geosearchengine.core.engine.operations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineErrorCode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineException;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineMessages;
import com.shyam.geosearchengine.repositories.GeoInfoRepository;
import com.shyam.geosearchengine.testutils.TestDataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeoInfoStatsFetcherTest {
    private GeoInfoRepository geoInfoRepository;
    private String name;

    @BeforeEach
    void setUp() {
        geoInfoRepository = mock(GeoInfoRepository.class);
        name = TestDataSet.getTestDataSets().get(0).getName();
    }

    @Test
    void test001ZeroMatches() {
        when(geoInfoRepository.findExactNameMatch(name)).thenReturn(
                0
        );
        when(geoInfoRepository.findPartialNameMatch(name)).thenReturn(
                0
        );
        GeoInfoStatsFetcher fetcher = new GeoInfoStatsFetcher(geoInfoRepository, name);
        try {
            Object object = fetcher.doOperation();
            assertNotNull(object, "output is null");
            assertTrue(object instanceof ObjectNode, "Object is not a ObjectNode");
            ObjectNode data = (ObjectNode) object;
            int exactMatchCount = data.get("exactMatchCount").asInt();
            int matchCount = data.get("matchCount").asInt();
            assertEquals(0, exactMatchCount);
            assertEquals(0, matchCount);
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    @Test
    void test002NonZeroMatches() {
        when(geoInfoRepository.findExactNameMatch(name)).thenReturn(
                3
        );
        when(geoInfoRepository.findPartialNameMatch(name)).thenReturn(
                5
        );
        GeoInfoStatsFetcher fetcher = new GeoInfoStatsFetcher(geoInfoRepository, name);
        try {
            Object object = fetcher.doOperation();
            assertNotNull(object, "output is null");
            assertTrue(object instanceof ObjectNode, "Object is not a ObjectNode");
            ObjectNode data = (ObjectNode) object;
            int exactMatchCount = data.get("exactMatchCount").asInt();
            int matchCount = data.get("matchCount").asInt();
            assertEquals(3, exactMatchCount);
            assertEquals(5, matchCount);
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    @Test
    void test003GeoInfoRepositoryNull() {

        try {
            GeoInfoStatsFetcher fetcher = new GeoInfoStatsFetcher(null, name);
            fetcher.doOperation();
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


}