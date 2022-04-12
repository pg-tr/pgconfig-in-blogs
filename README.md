# PostgreSQL Config Parameters in Blog Sites 

This repo is inspired from [postgresqlCO.NF](https://postgresqlco.nf/doc/en/param/). It searches PostgreSQL server parameters in blog posts, when it finds a parameter in a post, it writes that to the database with the parameter, blog title and blog link. It returns these blog posts as JSON documents when asked with the parameter. 

This service contains 2 different modules. The first one is Web Crawler which takes the values of blog URLs from a given pool and recursively goes over the blog and searches for PostgreSQL parameters if found written to the database.  The second one is a Restful API which takes PostgreSQL parameters and returns the related blog posts when asked.

## Blog Crawler 
The crawler module is a cron scheduled Spring Batch Job. When it runs it goes to the pool and pulls all Blog sites. Then iterate over these sites and recursive go deep and search PostgreSQL parameter. After the search is done, it writes found blog site URL, PostgreSQL parameter and title of the post to the database. These two processes run as batch jobs and not the Rest API. Due to the hardware capacity, the crawling is depth limited. 

### Setting Cron Config

Cron configuration is kept in the database. When the app builds it goes to the database and retrieves the cron configuration. After each run, it goes to the database and resets the cron config. So you can change the cron config after each iteration.

* Cron config keeps in cron_conf table.
* It retrieves the row with a key equal to 1. So do not add a new row just update the existing row. 
<br /> 
Examples:
<br /> 
`UPDATE cron_conf SET cron_exp = '0 */1 * * * *' WHERE id = 1;`

### Restful API 
The endpoint is JWT token authenticated therefore each user should have a unique JWT in jwt_token table. It takes a PostgreSQL parameter and returns the related blog posts. The application has a swagger configuration, thus the app can be tested in swagger. 



### How to package
`mvn clean package spring-boot:repackage -DskipTests` 

### How to run
`java -jar blogcrawling-0.0.1-SNAPSHOT.jar`

### How to run with Maven
`mvn clean spring-boot:run`

