plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.1")
}

kotlin {
    jvmToolchain(21)
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}
