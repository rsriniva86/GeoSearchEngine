package com.shyam.geosearchengine.core.engine.utils;

/**
 * This singleton class is for helping in validating if latitude and longitudes are within permissible limits
 */
public enum LatitudeLongitudeValidator {
    INSTANCE;

    public boolean isValidLatitude(double value) {
        if (value > 90) {
            return false;
        }
        if (value < -90) {
            return false;
        }
        return true;
    }

    public boolean isValidLongitude(double value) {
        if (value > 180) {
            return false;
        }
        if (value < -180) {
            return false;
        }
        return true;
    }
}
