FROM eclipse-temurin:17-jre-alpine
LABEL authors="steve7867"
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]
