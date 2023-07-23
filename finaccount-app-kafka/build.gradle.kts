plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("aigt.finaccounts.app.kafka.MainKt")
}

docker {

    /**
     * Для Linux, если есть проблема с пермишенами, необходимо
     * после установки докера добавить нерутового пользователя в группу
     * docker и перелогиниться:
     * https://docs.docker.com/engine/install/linux-postinstall/
     */
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("openjdk:17")
        maintainer.set("(c) Alex")
//        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

dependencies {
    val kafkaVersion: String by project
    val coroutinesVersion: String by project
    val atomicfuVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    // transport models
    implementation(project(":common"))
    implementation(project(":api-v1-jackson"))
    implementation(project(":api-v1-kmp"))
    implementation(project(":mappers-v1-jvm"))
    implementation(project(":mappers-v1-kmp"))

    // logic
    implementation(project(":biz"))

    testImplementation(kotlin("test-junit"))
}
