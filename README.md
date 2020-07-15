# Bookstore - Kotlin - Spring Webflux - R2DBC (Work In Progress)

A reactive stream implementation of a bookstore rest api writen in kotlin using spring webflux features with postgres 
through the newer R2DBC.

The Reactive Relational Database Connectivity (R2DBC) project brings reactive programming APIs to relational databases.

This project aims to be a demonstration as an use case of the R2DBC in a nearly production real project sample.

To take use of most features that R2DBC has to provide to us, 
the design of this project database was made in a normalized way because we think if you wanna use a relational database
 is because you wanna work with your data in some normalized level.


## Running The Application

You will need the docker-compose and jdk8+ installed on your machine.

**To run in your linux host, just enter in the root folder of this project:** 

```shell
$ docker-compose up
```
A postgres database and a pgadmin will up running
 in ports 5432 and 5050 respectivally.
 
After run:

```shell
$./gradlew update
```
To create all schema needed by the application in postgresql database

For last run:
```shell
$ ./gradlew bootrun
```

The application will start listening on http://localhost:8080 with a documentation in swagger
