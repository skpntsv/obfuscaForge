plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "ru.nsu.skopintsev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.8.9")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("ru.nsu.skopintsev.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}