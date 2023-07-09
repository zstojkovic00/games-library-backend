## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Features](#features)
* [Setup](#setup)
* [Acknowledgement](#acknowledgement)






## General info
Game Library is a place for you to keep track of your video game collection, It contains 500,000+ games for 50 platforms including mobiles. Once you sign up for an account, you can start adding games.
This project is made for learning purposes  and because I wanted to keep the memory of every game I've beaten.




## Technologies
Project is created with:
* Java
* Spring Boot
* Hibernate
* React.js
* Redux
* MySQL Database

<br/>

<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=idea,spring,hibernate,react,redux,mysql" />
  </a>
</p>




## Features
* Authentication and Authorization: The project implements authentication and authorization using Spring Security 6.0 with JWT (JSON Web Token) for secure user access and protection of sensitive endpoints.


* External API Integration: The application integrates with an external API to retrieve game data. The fetched game information is then stored in a local database for further processing.


* REST CRUD API: A comprehensive REST API is provided for managing user and game entities. The API is built using Spring Data JPA and Hibernate, enabling seamless interaction with the underlying database.


* Game Tracking: Once logged in, users can search for and add games they have played to their profile. The application keeps track of the games a user has completed.


* Time Tracking: The application records the average playtime for each game. Users can view the cumulative time they have spent playing games, providing an insight into how much of their life they have dedicated to gaming.


* Search Functionality: The project includes a search feature that allows users to find games based on their preferences. Users can search for games by various criteria, such as title, genre, or platform.


* Pagination: To enhance the user experience and optimize performance, pagination is implemented to divide large sets of data into smaller, more manageable pages.



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
