# Dockerfile for restock-platform
# Summary:
# This Dockerfile builds and run the restock-platform application using Maven and OpenJDK 24.
# Description:
# This Dockerfile is designed to build a Spring Boot application using Maven and run it in a lightweight
# OpenJDK 24 environment. It uses a multi-stage build to keep the final image size small by separating the build
# and runtime environments. It sets the active Spring profile to 'prod' for production use and exposes port 8080,
# which is the default port for Spring Boot applications.
# Version: 1.0
# Maintainer: Open-source Development Team

# Step 1: Build the application using Maven

# ---- Build ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# ---- Runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]


# Note: The application will run with the 'prod' profile as set in the build stage.
# This Dockerfile is designed to be used in a CI/CD pipeline or for local development.
# It is necessary to define the following environment variables in the hosting provider for the application to
# run correctly in the Production environment:
# - MONGODB_URI: The MongoDB connection URI (e.g. mongodb://localhost:27017).
# - MONGODB_DATABASE: The MongoDB database name (defaults to restock-bd).
# - PORT: The port on which the application will run (default is 8080).
# - SPRING_PROFILES_ACTIVE: The active Spring profile (Must be 'prod' to use the runtime configuration).