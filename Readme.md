# Spring Webflux

Spring WebFlux is an alternative to the traditional Spring MVC. It's used to create fully asynchronous and non-blocking application built on event-loop execution model. WebFlux internally uses Project Reactor and its publisher implementations Flux and Mono. It supports two programming models: annotation-based reactive components and functional routing and handling.

## Requirements

- Java 11+
- IntelliJ IDEA / Netbeans / Eclipse
- Docker (for MongoDB)

## Usage

1) Run docker-compose up in CLI to start a local MongoDB database

   The docker compose file should look like this (file included in project root directory):
    ```
    version: '3.3'

    services:
      mongodb:
      image: mongo:latest
      container_name: mongodb
      ports:
        - "27017:27017"
      volumes:
        - ~/apps/mongo:/data/db
    ``` 

2) Start application in IDE or via command line:

    ```
    ./gradlew bootRun
    ```  

3) Enjoy!

## Documentation

- [Swagger](http://localhost:9080/spring-webflux/swagger-ui.html) (for controller endpoints only)
- Included postman collection in project root folder for both controller and functional endpoints

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.  Please make sure to update tests as appropriate.

## License

Usage is provided under the [MIT License](https://mit-license.org/). See LICENSE for full details.