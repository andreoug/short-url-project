### Notes for demo: 


##### Be careful with: 
###### i. the settings of the DB in  the application.properites mysql:ip should be aligned with the ip of the mysql. (localhost vs mysql-server)
###### ii. the external path for the mysql data. I use "~/workspace_docker/mysql/mysqldata" for my db data, but it should be changed according to the OS.


##### package and build container's
mvn clean package dockerfile:build

##### RUN CONTAINERS
###### run db mysql container:
docker run -it -d  \
--name mysql-server -p 3306:3306 \
-v /data/:/Users/allemaos/workspace_docker/mysql/mysqldata \
-e MYSQL_ROOT_PASSWORD=admin \
-e MYSQL_DATABASE=demo \
-e MYSQL_USER=springuser \
-e MYSQL_PASSWORD=Password1 \
centurylink/mysql


###### run shorty container:
docker run  -t \
--name shorty -p 8080:8080 \
--link mysql-server:mysql \
shortio/shorty

###### run shorty-stats container:
docker run  -t \
--name shorty-stats -p 8081:8080 \
--link mysql-server:mysql \
shortio/shorty-stats

##### STOP AND REMOVE CONTAINERS
###### 1. shorty
docker stop shorty && docker rm shorty 
###### 2. mysql server
docker stop mysql-server && docker rm mysql-server 
###### 3. shorty-stats
docker stop shorty-stats && docker rm shorty-stats 