# Stage 1: Build the JAR with Maven
#FROM maven:3.8.6-openjdk-11 AS build
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests=true

# Stage 2: Create a smaller image for runtime
FROM openjdk:17-ea-jdk-alpine3.13
WORKDIR /app
COPY --from=build /app/target/taskBackendJenkins-1.jar /app/
EXPOSE 9000
CMD ["java", "-jar", "taskBackendJenkins-1.jar"]