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
* Java: 17
* Spring: Boot 3.0.2
* Hibernate: 6.1.6
* React.js: 18.2.0
* Redux: 8.0.5
* MySQL Database : 8.0.32

<br/>

<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=idea,spring,hibernate,react,redux,mysql" />
  </a>
</p>




## Features
* Authentication and Authorization created with spring security 6.0 - JWT
* Adding game from external API and storing it to a local database
* Created REST CRUD API for User and Game Entity (Spring Data JPA, Hibernate)
* Search so you can find whatever game you like
* Pagination is used to divide a document into pages



## Setup
To run this project, you need to download both game-library-frontend and game-library-backend files from my GitHub:

Requirements:
Java JDK 17, npm, node.js and MySQL with a database named 'user_security'


Frontend:
```
$ npm install
$ npm start
```

Backend:
```
mvn spring-boot:run 
```

## Acknowledgement

* Thanks to RAWG for providing easy to use and fast API
