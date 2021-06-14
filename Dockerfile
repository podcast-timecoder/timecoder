FROM gradle:5.6.1-jdk11 as compile
COPY . /home/source/java
WORKDIR /home/source/java
# Default gradle user is `gradle`. We need to add permission on working directory for gradle to build.
USER root
RUN chown -R gradle /home/source/java
USER gradle
RUN gradle clean build

FROM openjdk:11-jdk-slim
WORKDIR /home/application/java
COPY --from=compile "/home/source/java/build/libs/timecoder-api-service-1.4.0.jar" .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/home/application/java/timecoder-api-service-1.4.0.jar"]