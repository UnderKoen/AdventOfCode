plugins {
    java
    id("cup.gradle.cup-gradle-plugin") version "1.2"
    id("org.xbib.gradle.plugin.jflex") version "1.2.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly("org.projectlombok", "lombok", "1.18.16")
    annotationProcessor("org.projectlombok", "lombok", "1.18.16")

    implementation("info.picocli", "picocli", "4.5.2")
    annotationProcessor("info.picocli", "picocli-codegen", "4.5.2")

    implementation("com.google.guava", "guava", "30.0-jre")
    implementation("org.jgrapht", "jgrapht-core", "1.5.0")
    implementation("org.apache.httpcomponents", "httpclient", "4.5.13")
    implementation("org.apache.commons", "commons-lang3", "3.0")
    testImplementation("junit", "junit", "4.12")
}

tasks.cupCompile {
    dependsOn(tasks.clean)
    dependsOn(tasks.jflex)
}
