# PostgreSQL Config Paremeters in Blog Sites  

* A service crawls given blog sites and maps postgresql config parameters with blog contents and save them in postgresql.
* Another web service listens http requests and responds title and url of related blog as json when sending a postgresql configuration parameter to query parameter. 

### How to package
`mvn clean package spring-boot:repackage -DskipTests` 

### How to run
`java -jar blogcrawling-0.0.1-SNAPSHOT.jar`

### How to run with Maven
`mvn clean spring-boot:run`

