FROM openjdk:latest

ADD target/recipe-service-0.0.1-SNAPSHOT.jar .

EXPOSE 12127

ENTRYPOINT [java -jar recipe-service-0.0.1-SNAPSHOT.jar]
