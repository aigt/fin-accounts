@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project


// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(
    module: String,
    prefix: String = "server-",
    version: String? = this@Build_gradle.ktorVersion,
): Any = "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"


plugins {
    id("application")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    id("io.ktor.plugin")
}
dependencies {
    implementation(project(mapOf("path" to ":logging:lib:common")))
    implementation(project(mapOf("path" to ":logging:lib:common")))
    implementation(project(mapOf("path" to ":logging:lib:common")))
}


repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}


application {
    mainClass.set("aigt.finaccounts.app.ktor.Main_jvmKt")
}


ktor {
    docker {
        localImageName.set(project.name)
        imageTag.set(project.version.toString())
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
    }
}


jib {
    container.mainClass = "io.ktor.server.cio.EngineMain"
//    container.mainClass = "aigt.finaccounts.app.ApplicationJvmKt"
}


kotlin {
    jvm {
        withJava()
    }
    linuxX64 {}
    macosX64 {}
    macosArm64 {}

    /*val hostOs = System.getProperty("os.name")
    val arch = System.getProperty("os.arch")
    val nativeTarget = when {
        hostOs == "Mac OS X" && arch == "x86_64" -> macosX64("native")
        hostOs == "Mac OS X" && arch == "aarch64" -> macosArm64("native")
        hostOs == "Linux" -> linuxX64("native")
        // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }*/

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries {
            executable {
                entryPoint = "aigt.finaccounts.app.ktor.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"

                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktor("cio")) // "io.ktor:ktor-server-cio:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-server-auth:$ktorVersion"
                implementation(ktor("auto-head-response")) // "io.ktor:ktor-server-auto-head-response:$ktorVersion"
                implementation(ktor("caching-headers")) // "io.ktor:ktor-server-caching-headers:$ktorVersion"
                implementation(ktor("cors")) // "io.ktor:ktor-server-cors:$ktorVersion"
                implementation(ktor("websockets")) // "io.ktor:ktor-server-websockets:$ktorVersion"
                implementation(ktor("config-yaml")) // "io.ktor:ktor-server-config-yaml:$ktorVersion"
                implementation(ktor("content-negotiation")) // "io.ktor:ktor-server-content-negotiation:$ktorVersion"
                implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"

                //implementation("ch.qos.logback:logback-classic:$logbackVersion")

                implementation(project(":common"))
                implementation(project(":biz"))

                // kmp api
                implementation(project(":api-v1-kmp"))
                implementation(project(":mappers-v1-kmp"))

                // Stubs
                implementation(project(":stubs"))

                implementation(project(":logging:lib:common"))
                implementation(project(":logging:lib:kermit"))

                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(ktor("test-host"))
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

                // jackson
                implementation(
                    ktor(
                        "jackson",
                        "serialization"
                    )
                ) // io.ktor:ktor-serialization-jackson

                implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation
                implementation(
                    ktor(
                        "kotlinx-json",
                        "serialization"
                    )
                ) // io.ktor:ktor-serialization-kotlinx-json

                implementation(ktor("locations"))
                implementation(ktor("caching-headers"))
                implementation(ktor("call-logging"))
                implementation(ktor("auto-head-response"))
                implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("auto-head-response"))

                implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
                implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                // transport models
                implementation(project(":api-v1-jackson"))
                implementation(project(":mappers-v1-jvm"))
                implementation(project(":logging:lib:logback"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
            }
        }

        val linuxX64Main by getting
        val linuxX64Test by getting
    }
}


tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
}
