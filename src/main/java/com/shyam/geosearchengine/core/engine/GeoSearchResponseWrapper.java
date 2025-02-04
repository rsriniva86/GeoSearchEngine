package com.shyam.geosearchengine.core.engine;

import com.shyam.geosearchengine.core.JSONConstants;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineError;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineErrorCode;
import com.shyam.geosearchengine.core.engine.error.GeoSearchEngineException;
import com.shyam.geosearchengine.core.engine.error.GeoSearchResponseStatus;
import com.shyam.geosearchengine.core.engine.operations.GeoSearchEngineOperation;

import java.util.HashMap;
import java.util.Map;

public enum GeoSearchResponseWrapper {
    INSTANCE;

    /**
     * This method wraps the success and failure scenario during the operations.
     * Creates appropriate responses in the form of a Hashmap.
     * @param operation
     * @return
     */
    public Map<String, Object> wrap(GeoSearchEngineOperation operation) {
        Map<String, Object> returnMap = new HashMap();
        returnMap.put(JSONConstants.STATUS_TAG, GeoSearchResponseStatus.SUCCESS);
        try {
            returnMap.put(JSONConstants.CONTENT_TAG, operation.doOperation());
        } catch (GeoSearchEngineException e) {
            e.printStackTrace();
            returnMap.put(JSONConstants.STATUS_TAG, GeoSearchResponseStatus.FAILURE);
            returnMap.put(JSONConstants.ERROR_TAG, new GeoSearchEngineError(e.getCode().name(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put(JSONConstants.STATUS_TAG, GeoSearchResponseStatus.FAILURE);
            returnMap.put(JSONConstants.ERROR_TAG, new GeoSearchEngineError(GeoSearchEngineErrorCode.GENERIC.name(),
                    e.getMessage()));
        }
        return returnMap;
    }


}
