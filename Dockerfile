FROM maven:3.9.1 AS build
WORKDIR /app
COPY . .
RUN mvn clean package spring-boot:repackage

#
# Package stage
#
FROM openjdk:18-jdk-alpine
LABEL maintainer="rushit214@gmail.com"
WORKDIR /app
COPY --from=build /app .
EXPOSE 8080
ENTRYPOINT ["java", "-jar","target/ott-platform-project-docker.jar"]