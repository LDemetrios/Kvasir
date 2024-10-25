import org.ldemetrios.kvasir.build.writeColorsXml

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "org.ldemetrios"
version = "0.2.2"

repositories {
    mavenCentral()
    mavenLocal()
}

sourceSets["main"].java.srcDirs("src/main/gen")

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.1")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies {
    implementation("com.github.weisj:jsvg:1.6.0")

//    implementation("org.apache.xmlgraphics:batik-dom:+")
//    implementation("org.apache.xmlgraphics:batik-svg-dom:+")
//    implementation("org.apache.xmlgraphics:batik-parser:+")
//    implementation("org.apache.xmlgraphics:batik-bridge:+")
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

    runIde {
        dependsOn("prepareColors")
    }
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



