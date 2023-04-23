FROM gradle:7.4-jdk17 as build
COPY service /usr/HandymanService/src
WORKDIR /usr/HandymanService/src
RUN gradle build -x test

FROM openjdk:17
COPY --from=build /usr/HandymanService/src/build/libs/*.jar /usr/vogorode/HandymanService/HandymanService.jar
WORKDIR /usr/vogorode/HandymanService
EXPOSE 8080 9090
ENTRYPOINT ["java", "-jar", "HandymanService.jar"]
