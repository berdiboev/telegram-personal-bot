FROM eclipse-temurin:17-jdk

WORKDIR /app

# Копируем собранный JAR
COPY target/*.jar app.jar

# Koyeb передаёт порт через PORT env
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
