# Rohlic Technical test

## How to execute the software

Note: The application is pre-configured to drop and generate the database any time the application is started. In order
to change this behaviour set the variable *spring.jpa.hibernate.ddl-auto* with value *update* on docker-compose file.

#### Requirements:
* Install docker

#### Run application:
* Execute *docker-compose up* on root project path.

#### How to use the application:
On folder *endpoint-collection* there is a collection to be imported on Postman containing examples 
of all endpoints required.

* Configure PgAdmin:
  * * user: admin@rohlik.com
  * *  password: rohlik
  * Create DB connection:
    * Click on *Add New Server*
      * General > Name = rohlik-test
      * Connection > host = host.docker.internal
      * Connection > port = 5432
      * Connection > maintenance database = rohlik
      * Connection > rohlik
      * Connection > rohlik


## Areas to be improved
In this section will be explained some areas to be improved but were not done due to lack of time:

* Better test coverage: include Unit tests and Integration tests as well as improve the End2End test coverage.
* Error API management: improve error handling on API  