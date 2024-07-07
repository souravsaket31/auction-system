FROM openjdk:17-alpine

VOLUME /tmp
COPY target/auction-system-0.0.1-SNAPSHOT.jar auction-system.jar

ENTRYPOINT ["java", "-jar", "/auction-system.jar"]
