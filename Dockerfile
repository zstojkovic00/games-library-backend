FROM maven:3.8.4 AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package

FROM openjdk:17-jdk

WORKDIR /app

COPY --from=build /app/target/game-library-0.0.1-SNAPSHOT.jar /app/game-library.jar

EXPOSE 8080

CMD ["java", "-jar", "game-library.jar"]
