package com.shyam.GeoSearchEngine.repositories;

import com.shyam.GeoSearchEngine.models.db.TestDataDB;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TestDataRepository extends CrudRepository<TestDataDB,Long> {


    TestDataDB findById(long id);

    @Query("SELECT p FROM TESTDATA p WHERE LOWER(p.name) = LOWER(:name)")
    List<TestDataDB> find(@Param("name") String name);


    @Query("Select p FROM TESTDATA p WHERE p.location_id IN :pLocationIDList")
    List<TestDataDB> findWithinXKms(
            @Param("pLocationIDList") List<Long> pLocationIDList
    );

    @Query("SELECT COUNT(*) FROM TESTDATA p WHERE p.name =:pName")
    int findExactNameMatch(@Param("pName") String pName);

    @Query("SELECT COUNT(*) FROM TESTDATA p WHERE p.name LIKE %:pName%")
    int findPartialNameMatch(@Param("pName") String pName);

}
