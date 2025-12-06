FROM maven:3.9.5-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src /app/src

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-focal

WORKDIR /app

COPY --from=build /app/target/telegram-bot-1.0-SNAPSHOT.jar app.jar

ENV PORT 8080

ENTRYPOINT ["java", "-jar", "app.jar"]