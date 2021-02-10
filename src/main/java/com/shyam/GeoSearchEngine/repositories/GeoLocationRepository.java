package com.shyam.GeoSearchEngine.repositories;

import com.shyam.GeoSearchEngine.models.db.GeoLocation;
import com.shyam.GeoSearchEngine.models.db.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeoLocationRepository extends CrudRepository<GeoLocation,Long> {

    GeoLocation findByLocation(String location);

    @Query("Select gl,acos((sin(radians(:pLatitude))*sin(radians(gl.latitude))) + (cos(radians(:pLatitude))*cos(radians(gl.latitude))*cos(radians(gl.longitude)-radians(:pLongitude))))* 6371 as D FROM GEOLOCATIONS gl Where acos(sin(radians(:pLatitude))*sin(radians(gl.latitude)) + cos(radians(:pLatitude))*cos(radians(gl.latitude))*cos(radians(gl.longitude)-radians(:pLongitude))) * 6371 < :kmrange Order By D")
    List<GeoLocation> findWithinXKms(@Param("pLatitude") double pLatitude,
                               @Param("pLongitude") double pLongitude,
                               @Param("kmrange") double kmRange
    );


}
