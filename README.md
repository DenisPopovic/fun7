FUN7 - Software Engineer Expertise Test
===================================

# Prerequisites

* JAVA 17
* [PostgresSQL 15](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
    * Create new DB with name `fun7`
    * Using pgAdmin, import/restore the database using the provided backup .SQL file (file name: fun7DBBackup.sql).
* [Postman](https://www.postman.com/downloads/) for testing purposes
    * [Import Postman collection](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-data/)
      (file name: Fun7.postman_collection.json)

# DB

* For testing purposes, here is information about three user entries in the database:
    * `email`: denis.popovic@gmail.com, `password`: password, `role`: user, `login count`: 18
    * `email`: denis.popovic2@gmail.com, `password`: password2, `role`: admin, `login count`: 0
    * `email`: denis.popovic3@gmail.com, `password`: password3, `role`: user, `login count`: 5

# Testing

To run the application locally, execute the following command in the command line:

* In the `application.properties` file, change these variables.
    * `spring.datasource.url`=jdbc:postgresql://localhost:5432/fun7 (in case if you have different port or DB name
      please change it)
    * `spring.datasource.username`=postgres (change it to different user in case if you used different user in DB
      creation part)
    * `spring.datasource.password`=postgres (change it to your user password if it is set.)

* To run the application in your preferred command line, execute this command

```
java -jar fun7-0.0.1-SNAPSHOT.jar application.properties`
```

* In postman try these APIs:
    * `POST api/v1/auth/authenticate`
        * In the body, change the username and password for all three users and store the provided token for the next
          tests.
    * `GET api/v1/check-services`
        * Test with all users: The user with a login count of 18 will have multiplayer enabled, the user with a login
          count of 0 will have it disabled, and the admin user will not be able to access information for this call.
    * `GET api/v1/admin/users`
        * Test these api with admin user. Save uuid of specific user for next two API calls.
        * Try to run the same API call with a user who has the 'user' role.
    * `GET api/v1/admin/user/{userUUID}`
        * In case a user with the specified UUID exists, it will return details about that user.
        * In case a user with the specified UUID doesn't exist, it will return an appropriate error message.
        * Try to run the same API call with a user who has the 'user' role.
    * `DEL api/v1/admin/user/{userUUID}`
        * In case a user with the specified UUID exists, it will delete user.
        * In case a user with the specified UUID doesn't exist, it will return an appropriate error message.
        * In case a user with the specified his UUID, it will return an appropriate error message.
        * Try to run the same API call with a user who has the 'user' role.