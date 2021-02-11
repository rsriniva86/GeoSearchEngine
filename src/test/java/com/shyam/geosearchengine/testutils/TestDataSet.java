package com.shyam.geosearchengine.testutils;

import java.util.ArrayList;
import java.util.List;

public class TestDataSet {
    long id;
    String name;
    String location;
    double latitude;
    double longitude;
    long locationId;

    private TestDataSet(long id, String name, String location, double latitude, double longitude, long locationId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationId = locationId;
    }

    public static List<TestDataSet> getTestDataSets() {
        List<TestDataSet> list = new ArrayList<>();
        TestDataSet testDataSet = new TestDataSet(
                1,
                "21CNR",
                "Test1",
                1.348737, 103.969044,
                1);

        list.add(testDataSet);
        testDataSet = new TestDataSet(
                2,
                "28CNR",
                "Test2",
                1.3488196938853985, 103.97052082639239,
                2);
        list.add(testDataSet);
        testDataSet = new TestDataSet(
                3,
                "ARC",
                "Test3",
                1.2969133990837274, 103.78782071290127,
                3);
        list.add(testDataSet);
        testDataSet = new TestDataSet(
                4,
                "21CNR",
                "Test3",
                1.348737, 103.969044,
                1);
        list.add(testDataSet);
        testDataSet = new TestDataSet(
                5,
                "Test34",
                "Test1",
                1.348737, 103.969044,
                1);
        list.add(testDataSet);
        return list;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getLocationId() {
        return locationId;
    }
}
