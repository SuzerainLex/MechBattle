# Сборка из корня репозитория (Railway по умолчанию смотрит сюда)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY server/pom.xml .
COPY server/src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/mechbattle-server-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
