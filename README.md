"Game Library" is an application designed for users to catalog their video game collections, featuring over 500,000 games across 50 platforms, including mobile. It offers user authentication and authorization with Spring Security and JWT, as well as external API integration for retrieving and storing game data. The application provides a robust REST CRUD API for managing user and game entities, tracks completed games and playtime, offers search functionality, and implements pagination. This project is intended for learning purposes and aims to help users remember their gaming achievements. It's built using Java, Spring Boot, Hibernate, React.js, Redux, and a MySQL database.

## Setup
This project consists of a frontend and a backend, both of which need to be cloned and set up individually.

To run this project using Docker, make sure you have Docker and Docker Compose installed on your system. Then follow these steps:

Clone these repositories:

``` 
$ git clone https://github.com/zstojkovic00/games-library-backend.git
$ git clone https://github.com/zstojkovic00/games-library-frontend.git
```

Run the following command to start the application containers:
## Frontend
```
$ docker-compose up
```
## Backend
```
$ mvn clean package
$ docker build -t game-library .
$ docker-compose up
```


This command will build the required images and start the containers based on the provided Docker Compose configuration file.

## Acknowledgement

* Thanks to RAWG for providing easy to use and fast API
