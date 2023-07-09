FROM openjdk:17-jdk

WORKDIR /app

COPY target/game-library-0.0.1-SNAPSHOT.jar /app/game-library.jar

EXPOSE 8080

CMD ["java", "-jar", "game-library.jar"]
