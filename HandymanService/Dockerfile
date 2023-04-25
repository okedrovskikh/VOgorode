FROM gradle:7.4-jdk17 as build
COPY ./ /usr/HandymanService
WORKDIR /usr/HandymanService
RUN gradle build -x test

FROM openjdk:17-alpine3.14
COPY --from=build /usr/HandymanService/service/build/libs/*.jar /usr/vogorode/HandymanService/HandymanService.jar
WORKDIR /usr/vogorode/HandymanService
EXPOSE 8080 9090
ENTRYPOINT ["java", "-jar", "HandymanService.jar"]
