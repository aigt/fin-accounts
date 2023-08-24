rootProject.name = "fin-accounts"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false

        id("io.ktor.plugin") version ktorVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-spring-boot-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

// Приёмочные тесты
include("acceptance")

include("common")

// OpenApi генерация
include("api-v1-jackson")
include("api-v1-kmp")

include("mappers-v1-jvm")
include("mappers-v1-kmp")

include("biz")
include("stubs")

include("finaccount-app-ktor")
include("finaccount-app-kafka")

include(":logging:lib:common")
include(":logging:lib:kermit")
include(":logging:lib:logback")
include(":logging:v1:api")
include(":logging:v1:mappers")
