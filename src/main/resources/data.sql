INSERT INTO GEOLOCATIONS (location, latitude,longitude) VALUES
  ('21CNR', 1.348737,103.969044),
  ('28CNR', 1.3488196938853985, 103.97052082639239),
  ('ARC', 1.2969133990837274, 103.78782071290127);

  INSERT INTO GEOINFO (name,location_id) VALUES
  ('Test1',SELECT gl.id FROM GEOLOCATIONS gl WHERE location = '21CNR'),
  ('Test2',SELECT gl.id FROM GEOLOCATIONS gl WHERE location = '28CNR'),
  ('Test3',SELECT gl.id FROM GEOLOCATIONS gl WHERE location = 'ARC'),
  ('Test3',SELECT gl.id FROM GEOLOCATIONS gl WHERE location = '21CNR'),
  ('Test34',SELECT gl.id FROM GEOLOCATIONS gl WHERE location = '21CNR');