import org.ldemetrios.kvasir.build.writeColorsXml

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij") version "1.17.3"
    kotlin("plugin.serialization") version "2.1.0"
}

group = "org.ldemetrios"
version = "0.3.0"

repositories {
    mavenCentral()
    mavenLocal()
}

sourceSets["main"].java.srcDirs("src/main/gen")

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.3.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies {
    implementation("com.github.weisj:jsvg:1.6.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    testImplementation("io.kotest:kotest-runner-junit5:5.7.0")
    testImplementation("io.kotest:kotest-assertions-core:5.7.0")
    testImplementation("io.kotest:kotest-property:5.7.0")

    implementation("org.apache.commons:commons-compress:1.26.0")
//
//    implementation("net.java.dev.jna:jna:5.13.0")
//    implementation("net.java.dev.jna:jna-platform:5.13.0")
////    implementation("org.ldemetrios:tyko:0.4.0")


//    implementation("org.apache.xmlgraphics:batik-dom:+")
//    implementation("org.apache.xmlgraphics:batik-svg-dom:+")
//    implementation("org.apache.xmlgraphics:batik-parser:+")
//    implementation("org.apache.xmlgraphics:batik-bridge:+")
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

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

//    /*withType<JavaExec>*/ runIde {
//        val list = listOf(
//            "java.base/java.lang",
//            "java.base/java.util",
//            "java.base/jdk.internal.misc",
//            "java.base/java.security",
//            "java.desktop/javax.swing.event",
//            "java.base/java.util.concurrent",
//            "java.base/java.util.concurrent.locks",
//            "java.base/sun.reflect.annotation",
//            "java.datatransfer/java.awt.datatransfer",
//            "java.desktop/sun.java2d.xr",
//            "java.base/jdk.internal.ref",
//                    "java.base/java.util.concurrent.locks",
//        )
//        for (el in list) {
//            jvmArgs("--add-opens", "$el=ALL-UNNAMED")
//        }
//    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

//    runIde {
//        dependsOn("prepareColors")
//    }
//
//    patchPluginXml {
//        sinceBuild.set("232")
//        untilBuild.set(null as String?)
//    }

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



