#Readme
##Build
###Application jar file
```./gradlew bootJar```
##Docker
###Build image (need to build jar file first)
```docker build -t geosearchengine:latest .```
##Run image
````docker run -d -p 9090:8080 geosearchengine````
##Verify docker run
```docker ps```