FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir -p /usr/app
WORKDIR /usr/app
COPY . /usr/app
RUN mvn clean package -Dmaven.test.skip=true -Dspring.profiles.active=deploy

FROM openjdk:17-jdk-alpine AS tomcat
WORKDIR /usr/app
RUN apk update
RUN apk add --no-cache bash
COPY --from=build /usr/app/target/*.jar /usr/app/app.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=deploy -jar /usr/app/app.jar"]