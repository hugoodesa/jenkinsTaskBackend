FROM openjdk:17-ea-jdk-alpine3.13
WORKDIR /app
COPY ./target/taskBackendJenkins-1.jar .
EXPOSE 9000
CMD ["java", "-jar", "taskBackendJenkins-1.jar"]