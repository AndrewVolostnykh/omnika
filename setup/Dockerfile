FROM openjdk:11-jre-slim

ARG jar_file
COPY ${jar_file} /usr/src/service.jar

CMD java -jar /usr/src/service.jar
