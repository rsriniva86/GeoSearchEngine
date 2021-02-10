package com.shyam.GeoSearchEngine.repositories;

import com.shyam.GeoSearchEngine.models.db.DBPlace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PlacesRepository extends CrudRepository<DBPlace,Long> {


    DBPlace findById(long id);

    @Query("SELECT p FROM PLACES p WHERE LOWER(p.name) = LOWER(:name)")
    List<DBPlace> find(@Param("name") String name);


    @Query("Select p FROM PLACES p WHERE p.location_id IN :pLocationIDList")
    List<DBPlace> findWithinXKms(
            @Param("pLocationIDList") List<Long> pLocationIDList
    );

    @Query("SELECT COUNT(*) FROM PLACES p WHERE p.name =:pName")
    int findExactNameMatch(@Param("pName") String pName);

    @Query("SELECT COUNT(*) FROM PLACES p WHERE p.name LIKE %:pName%")
    int findPartialNameMatch(@Param("pName") String pName);

}
