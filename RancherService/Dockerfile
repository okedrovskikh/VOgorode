FROM gradle:7.4-jdk17 as build
COPY ./ /usr/RancherService
WORKDIR /usr/RancherService
RUN gradle build -x test

FROM openjdk:17-alpine3.14
COPY --from=build /usr/RancherService/service/build/libs/*.jar /usr/vogorode/RancherService/RancherService.jar
WORKDIR /usr/vogorode/RancherService
EXPOSE 8081 9091
ENTRYPOINT ["java", "-jar", "RancherService.jar"]
