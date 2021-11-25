plugins {
    java
    id("cup.gradle.cup-gradle-plugin") version "2.0"
    id("org.xbib.gradle.plugin.jflex") version "1.5.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

sourceSets["main"].java {
    srcDir("src/main/cup")
    srcDir("src/main/jflex")
}

dependencies {
    compileOnly("org.projectlombok", "lombok", "1.18.22")
    annotationProcessor("org.projectlombok", "lombok", "1.18.22")

    implementation("info.picocli", "picocli", "4.6.2")
    annotationProcessor("info.picocli", "picocli-codegen", "4.6.2")

    implementation("org.jetbrains", "annotations", "23.0.0")
    implementation("com.google.guava", "guava", "31.0.1-jre")
    implementation("org.jgrapht", "jgrapht-core", "1.5.1")
    implementation("org.apache.httpcomponents", "httpclient", "4.5.13")
    implementation("org.apache.commons", "commons-lang3", "3.12.0")
    testImplementation("junit", "junit", "4.12")
}

tasks.cupCompile {
    dependsOn(tasks.clean)
    finalizedBy(tasks.build)
}