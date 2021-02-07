package com.shyam.GeoSearchEngine.repositories;

import com.shyam.GeoSearchEngine.models.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PlacesRepository extends CrudRepository<Place,Long> {

    List<Place> findByName(String name);
    Place findById(long id);
    List<Place> findByLocation(String name);

    @Query("SELECT p FROM PLACES p WHERE LOWER(p.name) = LOWER(:name)")
    List<Place> find(@Param("name") String name);


//Select fp.ID ,fp.NAME,fp.LOCATION,fp.LONGITUDE,fp.LATITUDE,
//    acos(
//            (sin(radians(1.348737))*sin(radians(fp.LATITUDE)))+
//            (cos(radians(1.348737))*cos(radians(fp.LATITUDE))*cos(radians(fp.LONGITUDE)-radians(103.969044)))) * 6371  as D
//    From (Select p.ID ,p.NAME,p.LOCATION,p.LONGITUDE,p.LATITUDE
//            From PLACES p
//                  Where p.latitude Between 1.2 And 1.6
//                  And p.longitude Between 98 And 108) As fp
//    Where
//    acos(sin(radians(1.348737))*sin(radians(fp.LATITUDE ))+
//    cos(radians(1.348737))*cos(radians(fp.LATITUDE ))*cos(radians(fp.LONGITUDE )-radians(103.969044))) * 6371 < 10
//    Order By D

//
//    @Query("Select " +
//            "fp.ID ,fp.NAME,fp.LOCATION,fp.LONGITUDE,fp.LATITUDE,\n" +
//            "acos(" +
//                    "(" +
//                        "sin(" +
//                            "radians(:pLatitude)" +
//                            ")" +
//                        "*" +
//                        "sin(" +
//                            "radians(fp.LATITUDE)" +
//                            ")" +
//                    ")" +
//                    " + " +
//                    "(" +
//                        "cos(" +
//                            "radians(:pLatitude)" +
//                        ")" +
//                        "*" +
//                        "cos(" +
//                            "radians(fp.LATITUDE)" +
//                        ")" +
//                        "*" +
//                        "cos(" +
//                            "radians(fp.LONGITUDE)-radians(:pLongitude)" +
//                        ")" +
//                    ")" +
//                ")" +
//            "* 6371  as D\n" +
//            " FROM ( Select p.ID ,p.NAME,p.LOCATION,p.LONGITUDE,p.LATITUDE\n" +
//                            " FROM PLACES p\n" +
//                                " Where p.latitude Between :minLat And :maxLat\n" +
//                                    " And p.longitude Between :minLon And :maxLon\n" +
//                    ") As fp\n" +
//            " Where " +
//                "acos(" +
//                    "sin(" +
//                        "radians(:pLatitude)" +
//                        ")" +
//                    "*" +
//                    "sin(" +
//                        "radians(fp.LATITUDE)" +
//                        ")" +
//                    " + " +
//                    "cos(" +
//                        "radians(:pLatitude)" +
//                        ")" +
//                    "*" +
//                    "cos(" +
//                        "radians(fp.LATITUDE)" +
//                        ")" +
//                    "*" +
//                    "cos(" +
//                        "radians(fp.LONGITUDE)-radians(:pLongitude)" +
//                        ")" +
//                    ") * 6371 < :kmrange " +
//            "Order By D")
//
//    @Query("Select fp.ID,fp.NAME,fp.LOCATION,fp.LONGITUDE,fp.LATITUDE,acos((sin(radians(:pLatitude))*sin(radians(fp.LATITUDE))) + (cos(radians(:pLatitude))*cos(radians(fp.LATITUDE))*cos(radians(fp.LONGITUDE)-radians(:pLongitude))))* 6371 as D FROM ( Select p.ID,p.NAME,p.LOCATION,p.LONGITUDE,p.LATITUDE FROM Places p Where p.latitude Between :minLat And :maxLat And p.longitude Between :minLong And :maxLong ) As fp Where acos(sin(radians(:pLatitude))*sin(radians(fp.LATITUDE)) + cos(radians(:pLatitude))*cos(radians(fp.LATITUDE))*cos(radians(fp.LONGITUDE)-radians(:pLongitude))) * 6371 < :kmrange Order By D")
//    List<Place> findWithinXKms(@Param("pLatitude") double pLatitude,
//                               @Param("pLongitude") double pLongitude,
//                               @Param("minLat") double minLat,
//                               @Param("minLon") double minLon,
//                               @Param("maxLat") double maxLat,
//                               @Param("maxLon") double maxLon,
//                               @Param("kmrange") double kmRange
//    );


//    @Query("Select fp,acos((sin(radians(:pLatitude))*sin(radians(fp.latitude))) + (cos(radians(:pLatitude))*cos(radians(fp.latitude))*cos(radians(fp.longitude)-radians(:pLongitude))))* 6371 as D FROM " +
//            "\n (Select p FROM PLACES p Where p.latitude Between :minLat And :maxLat And p.longitude Between :minLon And :maxLon) as fp" +
//            "\n Where acos(sin(radians(:pLatitude))*sin(radians(fp.latitude)) + cos(radians(:pLatitude))*cos(radians(fp.latitude))*cos(radians(fp.longitude)-radians(:pLongitude))) * 6371 < :kmrange Order By D")
//    List<Place> findWithinXKms(@Param("pLatitude") double pLatitude,
//                               @Param("pLongitude") double pLongitude,
//                               @Param("minLat") double minLat,
//                               @Param("minLon") double minLon,
//                               @Param("maxLat") double maxLat,
//                               @Param("maxLon") double maxLon,
//                               @Param("kmrange") double kmRange
//    );


    //Working code
    @Query("Select fp,acos((sin(radians(:pLatitude))*sin(radians(fp.latitude))) + (cos(radians(:pLatitude))*cos(radians(fp.latitude))*cos(radians(fp.longitude)-radians(:pLongitude))))* 6371 as D FROM PLACES fp Where acos(sin(radians(:pLatitude))*sin(radians(fp.latitude)) + cos(radians(:pLatitude))*cos(radians(fp.latitude))*cos(radians(fp.longitude)-radians(:pLongitude))) * 6371 < :kmrange Order By D")
    List<Place> findWithinXKms(@Param("pLatitude") double pLatitude,
                               @Param("pLongitude") double pLongitude,
                               @Param("kmrange") double kmRange
    );

    @Query("SELECT COUNT(*) FROM PLACES p WHERE p.name =:pName")
    int findExactNameMatch(@Param("pName") String pName);

    @Query("SELECT COUNT(*) FROM PLACES p WHERE p.name LIKE %:pName%")
    int findPartialNameMatch(@Param("pName") String pName);

//    ("UPDATE PLACES p SET p.latitude=:pLatitude,p.longitude=:pLongitude WHERE p.id =':pID'")
//    int updateGeoPoint(@Param("pID") int pID,
//                       @Param("pLatitude") double pLatitude,
//                       @Param("pLongitude") double pLongitude);
}
