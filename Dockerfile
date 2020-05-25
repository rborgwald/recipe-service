FROM openjdk:latest

RUN mkdir /opt/recipe/
RUN mkdir /images
RUN apt-get update -y
RUN apt-get install maven -y
ADD . /opt/recipe/
WORKDIR /opt/recipe
RUN mvn clean install

EXPOSE 12127

ENTRYPOINT [java /opt/recipe/target/recipe-service-0.0.1-SNAPSHOT.jar]
