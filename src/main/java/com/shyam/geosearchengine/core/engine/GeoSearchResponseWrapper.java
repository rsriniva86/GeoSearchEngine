package com.shyam.geosearchengine.core.engine;

import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineError;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineErrorCode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineException;
import com.shyam.geosearchengine.core.engine.error.GeoSearchResponseStatus;
import com.shyam.geosearchengine.core.engine.operations.GeoSearchEngineOperation;

import java.util.HashMap;
import java.util.Map;

public enum GeoSearchResponseWrapper {
    INSTANCE;

    //TODO
    public Map<String, Object> wrap(GeoSearchEngineOperation operation) {
        Map<String, Object> returnMap = new HashMap();
        returnMap.put("status", GeoSearchResponseStatus.SUCCESS);
        try {
            returnMap.put("content", operation.doOperation());
        } catch (GeoSearchEngineException e) {
            e.printStackTrace();
            returnMap.put("status", GeoSearchResponseStatus.FAILURE);
            returnMap.put("error", new GeoSearchEngineError(e.getCode().name(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("status", GeoSearchResponseStatus.FAILURE);
            returnMap.put("error", new GeoSearchEngineError(GeoSearchEngineErrorCode.GENERIC.name(),
                    e.getMessage()));
        }
        return returnMap;
    }


}
