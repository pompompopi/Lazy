plugins {
    id("java-library")
    id("maven-publish")
}

group = "me.pompompopi"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")
}

tasks {
    withType<Jar> {
        from("LICENSE") { rename { "${project.name.lowercase()}_$it" } }
    }
}