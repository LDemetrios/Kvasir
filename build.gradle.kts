import org.jetbrains.intellij.tasks.RunIdeTask
import org.ldemetrios.kvasir.build.writeColorsXml

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    id("org.jetbrains.intellij") version "1.17.3"
    kotlin("plugin.serialization") version "2.3.0"
}

group = "org.ldemetrios"
version = "0.4.0"

repositories {
    mavenCentral()
    mavenLocal()
}

sourceSets["main"].java.srcDirs("src/main/gen")

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    api(kotlin("reflect"))

    api("org.ldemetrios:tyko-runtime:0.5.a")
    api("org.ldemetrios:tyko-drivers-chicory-based:0.5.a")

    api("com.github.weisj:jsvg:1.6.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.7.0")
    testImplementation("io.kotest:kotest-assertions-core:5.7.0")
    testImplementation("io.kotest:kotest-property:5.7.0")
}

tasks.register("prepareColors") {
    doLast {
        fun theme(name: String) = writeColorsXml(
            File("resources/commons.json"),
            File("resources/$name.json"),
            File("src/main/resources/kvasir-color-schemes/$name.xml")
        )
        theme("high-contrast")
        theme("light")
        theme("darcula")
    }
}

intellij {
    updateSinceUntilBuild.set(false)
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    buildPlugin {

    }

    signPlugin {
        certificateChainFile.set(file("credentials/chain.crt"))
        privateKeyFile.set(file("credentials/private.pem"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

//tasks.withType<RunIdeTask>().configureEach {
//    jvmArgs("-Xms1g", "-Xmx4g")
//}
//
