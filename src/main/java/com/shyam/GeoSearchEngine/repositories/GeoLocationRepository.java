package com.shyam.GeoSearchEngine.repositories;

import com.shyam.GeoSearchEngine.models.db.GeoLocationDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository class for GeoLocation table
 */
public interface GeoLocationRepository extends CrudRepository<GeoLocationDB, Long> {

    GeoLocationDB findByLocation(String location);

    /**
     * This is the main query method to get the all the GeoLocations in database within kmRange of given latitude
     * and longitude. The query is based on http://janmatuschek.de/LatitudeLongitudeBoundingCoordinates
     * kmRange= {@link com.shyam.GeoSearchEngine.core.AppConfiguration#DISTANCE_RANGE}
     *
     * @param pLatitude
     * @param pLongitude
     * @param kmRange
     * @return
     */
    @Query("Select gl,acos((sin(radians(:pLatitude))*sin(radians(gl.latitude))) + (cos(radians(:pLatitude))*cos(radians(gl.latitude))*cos(radians(gl.longitude)-radians(:pLongitude))))* 6371 as D FROM GEOLOCATIONS gl Where acos(sin(radians(:pLatitude))*sin(radians(gl.latitude)) + cos(radians(:pLatitude))*cos(radians(gl.latitude))*cos(radians(gl.longitude)-radians(:pLongitude))) * 6371 < :kmrange Order By D")
    List<GeoLocationDB> findWithinXKms(@Param("pLatitude") double pLatitude,
                                       @Param("pLongitude") double pLongitude,
                                       @Param("kmrange") double kmRange
    );


}
