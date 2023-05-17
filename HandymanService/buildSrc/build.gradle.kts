 plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("kapt") version "1.8.20"
}

group = "ru.tinkoff.academy"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("net.datafaker:datafaker:1.9.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
}
