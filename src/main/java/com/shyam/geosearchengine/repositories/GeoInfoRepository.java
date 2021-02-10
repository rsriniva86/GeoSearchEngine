package com.shyam.geosearchengine.repositories;

import com.shyam.geosearchengine.models.GeoInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository class for test data
 */
public interface GeoInfoRepository extends CrudRepository<GeoInfo, Long> {


    GeoInfo findById(long id);

    @Query("SELECT p FROM GEOINFO p WHERE LOWER(p.name) = LOWER(:name)")
    List<GeoInfo> find(@Param("name") String name);


    @Query("Select p FROM GEOINFO p WHERE p.location_id IN :pLocationIDList")
    List<GeoInfo> findWithinXKms(
            @Param("pLocationIDList") List<Long> pLocationIDList
    );

    @Query("SELECT COUNT(*) FROM GEOINFO p WHERE p.name =:pName")
    int findExactNameMatch(@Param("pName") String pName);

    @Query("SELECT COUNT(*) FROM GEOINFO p WHERE p.name LIKE %:pName%")
    int findPartialNameMatch(@Param("pName") String pName);

}
