package com.shyam.GeoSearchEngine.core.geosearchengine.error;

public class GeoSearchEngineException extends Exception {

    private final GeoSearchEngineErrorCode code;

    public GeoSearchEngineException(GeoSearchEngineErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public GeoSearchEngineErrorCode getCode() {
        return code;
    }
}
