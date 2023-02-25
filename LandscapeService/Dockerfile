FROM openjdk:17 as build
COPY ./ /usr/LandscapeService/src
WORKDIR /usr/LandscapeService/src
RUN ./gradlew build

FROM openjdk:17
COPY --from=build /usr/LandscapeService/src/build/libs/*.jar /usr/vogorode/LanscapeService/LandscapeService.jar
WORKDIR /usr/vogorode/LandscapeService
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "./LandscapeService.jar"]
