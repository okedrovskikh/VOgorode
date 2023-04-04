FROM openjdk:17 as build
COPY ./ /usr/HandymanService/src
WORKDIR /usr/HandymanService/src
RUN ./gradlew build -x test

FROM openjdk:17
COPY --from=build /usr/HandymanService/src/build/libs/*.jar /usr/vogorode/HandymanService/HandymanService.jar
WORKDIR /usr/vogorode/HandymanService
EXPOSE 8080 9090
ENTRYPOINT ["java", "-jar", "HandymanService.jar"]
