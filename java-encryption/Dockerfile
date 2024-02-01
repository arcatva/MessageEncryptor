FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:21
WORKDIR /app
COPY --from=build /home/app/target/java-encryption-1.0-SNAPSHOT.jar /app
CMD ["java", "-jar", "/app/java-encryption-1.0-SNAPSHOT.jar"]