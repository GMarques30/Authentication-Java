FROM maven:3.6.3-openjdk-17-slim AS build

WORKDIR /usr/src/app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

RUN ls -l /usr/src/app/target

FROM openjdk:17-jdk-alpine

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/target/*.jar ./app.jar

EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]