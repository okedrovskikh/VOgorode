import com.google.protobuf.gradle.id

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("java")
    id("com.google.protobuf") version "0.9.2"
}

group = "ru.tinkoff.academy"
version = "1.13.0"

java {
    toolchain {
        version = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
    // confluent maven repo
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.findByName("annotationProcessor"))
    }
}

extra["testcontainersVersion"] = "1.17.4"
extra["springCloudVersion"] = "2022.0.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("net.devh:grpc-client-spring-boot-starter:2.14.0.RELEASE")
    implementation("org.liquibase:liquibase-core")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.confluent:kafka-protobuf-serializer:7.3.0") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
        exclude(group = "org.apache.kafka", module = "kafka-clients")
    }
    compileOnly("org.projectlombok:lombok")
    compileOnly("org.mapstruct:mapstruct:1.5.3.Final")
    compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5") // only for generated protobuf classes
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${extra.get("testcontainersVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
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