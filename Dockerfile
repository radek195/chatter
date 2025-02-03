FROM openjdk:17-alpine
COPY target/chatter-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]