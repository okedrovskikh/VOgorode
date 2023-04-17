import com.google.protobuf.gradle.id

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("java")
    id("com.google.protobuf") version "0.9.2"
}

group = "ru.tinkoff.academy"
version = "1.8.0"

java {
    toolchain {
        version = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.findByName("annotationProcessor"))
    }
}

ext {
    set("testcontainersVersion", "1.17.4")
}

dependencies {
    implementation("org.springframework.data:spring-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework:spring-aspects")
    implementation("net.devh:grpc-spring-boot-starter:2.14.0.RELEASE")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.liquibase:liquibase-core")
    implementation("org.mongodb:mongodb-driver-sync")
    implementation("org.hibernate:hibernate-spatial:6.1.7.Final")
    compileOnly("org.projectlombok:lombok")
    compileOnly("org.mapstruct:mapstruct:1.5.3.Final")
    compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:mongodb")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${extra.get("testcontainersVersion")}")
    }
}

tasks {
    compileJava {
        options.encoding ="UTF-8"
    }

    compileTestJava {
        options.encoding = "UTF-8"
    }

    springBoot {
        buildInfo()
    }

    test {
        useJUnitPlatform()
    }

    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:3.22.2"
        }
        plugins {
            id("grpc") {
                artifact = "io.grpc:protoc-gen-grpc-java:1.54.0"
            }
        }
        generateProtoTasks {
            ofSourceSet("main").forEach {
                it.plugins {
                    id("grpc") { }
                }
            }
        }
    }
}
