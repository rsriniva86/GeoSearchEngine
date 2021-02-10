package com.shyam.GeoSearchEngine.core.geosearchengine;

import com.shyam.GeoSearchEngine.core.geosearchengine.error.GeoSearchEngineError;
import com.shyam.GeoSearchEngine.core.geosearchengine.error.GeoSearchEngineErrorCode;
import com.shyam.GeoSearchEngine.core.geosearchengine.error.GeoSearchEngineException;
import com.shyam.GeoSearchEngine.core.geosearchengine.operations.GeoSearchEngineOperation;

import java.util.HashMap;
import java.util.Map;

public enum GeoSearchResponseWrapper {
    INSTANCE;
    public Map<String,Object> wrap(GeoSearchEngineOperation operation){
        Map<String,Object> returnMap=new HashMap();

        returnMap.put("success",true);
        try {
            returnMap.put("content",operation.doOperation());
        }catch (GeoSearchEngineException e) {
            returnMap.put("success",false);
            returnMap.put("error",new GeoSearchEngineError(e.getCode().name(),e.getMessage()));
        }
        catch (Exception e) {
            returnMap.put("success",false);
            returnMap.put("error",new GeoSearchEngineError(GeoSearchEngineErrorCode.GENERIC.name(),
                    e.getMessage()));
        }
        return returnMap;
    }


}
