package com.shyam.geosearchengine.core.engine.error;

public class GeoSearchEngineError {

    private String code;
    private String message;

    public GeoSearchEngineError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
