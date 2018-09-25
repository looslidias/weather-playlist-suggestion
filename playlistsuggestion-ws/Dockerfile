FROM openjdk:8-alpine

MAINTAINER rafaeldias@gmail.com

RUN apk update && apk add bash

RUN mkdir -p /opt/app

ENV PROJECT_HOME /opt/app

COPY target/playlistsuggestion-ws-0.0.1-SNAPSHOT.jar $PROJECT_HOME/playlistsuggestion-ws.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"./playlistsuggestion-ws.jar"]