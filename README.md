# marketplace-java

Simple marketplace where users can add products and sell. 

## Prerequisites

What you need to start

[JAVA 11+](https://adoptopenjdk.net/) \
[PostgreSQL](https://www.postgresql.org/download/)\
[MongoDB](https://www.mongodb.com/try/download/community)\
[ActiveMQ/Artemis](https://activemq.apache.org/components/artemis/download/)\
[AWS Account](https://aws.amazon.com/)\
[Gmail](https://accounts.google.com/signup/)

Nice To have\
[Postman](https://www.postman.com/downloads/)\
[Robo3T](https://robomongo.org/download)

## Installation

There are several configurations that you need to do to start the project.\
Create user for PostgreSQL - example `user-service/src/main/scripts/database-init.sql` \
Create user for MongoDB - example `product-service/src/main/scripts/mongo-init.sh` \
After creating users set corresponding credentials in `application.properties` files

Create `IAM` user with full access on `Amazon S3` and set keys as a `environment variable` for `image-service`

Create gmail account and set credentials as a `environment variable` for `mail-service`

[Run Artemis](http://activemq.apache.org/components/artemis/documentation/1.0.0/running-server.html)

## Project status

The project is still in progress and there are a lot of thing to do.
1) Implement purchase logic
2) Implement `ADMIN` Role endpoints
2) Write tests) A lot of tests)
3) Add Ribbon and Feign Clients

## Disclaimer

The provided codes are not ready for production and should only be used for education purposes.

