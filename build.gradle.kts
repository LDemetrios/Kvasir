import org.ldemetrios.kvasir.build.writeColorsXml

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "org.ldemetrios"
version = "a.1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

sourceSets["main"].java.srcDirs("src/main/gen")

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.6")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies {
    implementation("com.github.ajalt.colormath:colormath:3.6.0")
}

task("prepareColors") {
    doLast {
        fun theme(name: String) = writeColorsXml(
            file("resources/commons.json"),
            file("resources/$name.json"),
            file("src/main/resources/kvasir-color-schemes/$name.xml")
        )
        theme("high-contrast")
        theme("light")
        theme("darcula")
    }
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

    runIde {
        dependsOn("prepareColors")
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    buildPlugin {

    }

    signPlugin {
        certificateChainFile.set(file("credentials/chain.crt"))
        privateKeyFile.set(file("credentials/private.pem"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        doFirst{
            throw AssertionError("Publish Plugin task is forbidden")
        }
        dependsOn("prepareColors")
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
