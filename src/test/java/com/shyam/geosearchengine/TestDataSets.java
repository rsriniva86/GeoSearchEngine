package com.shyam.geosearchengine;

import java.util.ArrayList;
import java.util.List;

public class TestDataSets {


        long ID ;
        String NAME;
        String LOCATION;
        double LATITUDE;
        double LONGITUDE;
        long LOCATION_ID;

    private TestDataSets(long ID, String NAME, String LOCATION, double LATITUDE, double LONGITUDE, long LOCATION_ID) {
        this.ID = ID;
        this.NAME = NAME;
        this.LOCATION = LOCATION;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.LOCATION_ID = LOCATION_ID;
    }

    public static List<TestDataSets> getTestDataSets(){
        TestDataSets testDataSets=new TestDataSets(1,"21CNR","Test1",1.348737,103.969044,1);
        List<TestDataSets> list=new ArrayList<>();
        list.add(testDataSets);
        return list;
    }


    public long getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public long getLOCATION_ID() {
        return LOCATION_ID;
    }
}
