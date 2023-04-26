FROM gradle:7.4-jdk17 as build
COPY ./ /usr/LandscapeService
WORKDIR /usr/LandscapeService
RUN gradle build -x test

FROM openjdk:17-alpine3.14
COPY --from=build /usr/LandscapeService/src/build/libs/*.jar /usr/vogorode/LandscapeService/LandscapeService.jar
WORKDIR /usr/vogorode/LandscapeService
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "LandscapeService.jar"]
