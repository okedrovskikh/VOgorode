FROM openjdk:17 as build
COPY ./ /usr/LandscapeService/src
WORKDIR /usr/LandscapeService/src
RUN ./gradlew build -x test

FROM openjdk:17
COPY --from=build /usr/LandscapeService/src/build/libs/*.jar /usr/vogorode/LandscapeService/LandscapeService.jar
WORKDIR /usr/vogorode/LandscapeService
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "LandscapeService.jar"]
