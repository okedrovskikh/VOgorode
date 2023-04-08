plugins {
    id 'org.springframework.boot' version '2.7.5'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
    id 'java'
    id 'com.google.protobuf' version "0.9.1"
}

group 'ru.tinkoff.academy'
version '1.6.0'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

ext {
    set('testcontainersVersion', "1.17.4")
}

dependencies {
    implementation 'org.springframework.data:spring-data-mongodb'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'net.devh:grpc-spring-boot-starter:2.13.1.RELEASE'
    implementation 'io.micrometer:micrometer-registry-prometheus:1.10.3'
    implementation 'org.mongodb:mongodb-driver-sync'
    compileOnly 'org.projectlombok:lombok'
    compileOnly 'org.mapstruct:mapstruct:1.5.3.Final'
    runtimeOnly 'org.postgresql:postgresql'
    annotationProcessor 'org.projectlombok:lombok'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.3.Final'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.testcontainers:junit-jupiter'
}

dependencyManagement {
    imports {
        mavenBom "org.testcontainers:testcontainers-bom:${testcontainersVersion}"
    }
}

compileJava {
    options.encoding('UTF-8')
}

compileTestJava {
    options.encoding('UTF-8')
}

springBoot {
    buildInfo()
}

test {
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.6"
    }
    plugins {
        grpc {
            artifact = 'io.grpc:protoc-gen-grpc-java:1.42.2'
        }
    }
    generateProtoTasks {
        all()*.plugins {
            grpc {}
        }
    }
}
