
DROP TABLE IF EXISTS GEOINFOS;
DROP TABLE IF EXISTS GEOLOCATIONS;

CREATE TABLE GEOLOCATIONS(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    location VARCHAR(250) NOT NULL UNIQUE,
    longitude DOUBlE NOT NULL,
    latitude DOUBLE NOT NULL
);
CREATE TABLE GEOINFOS (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    location_id INT NOT NULL,
    FOREIGN KEY(location_id) REFERENCES GEOLOCATIONS(id)
  );





