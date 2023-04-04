FROM openjdk:17 as build
COPY ./ /usr/RancherService/src
WORKDIR /usr/RancherService/src
RUN ./gradlew build -x test

FROM openjdk:17
COPY --from=build /usr/RancherService/src/build/libs/*.jar /usr/vogorode/RancherService/RancherService.jar
WORKDIR /usr/vogorode/RancherService
EXPOSE 8081 9091
ENTRYPOINT ["java", "-jar", "RancherService.jar"]
