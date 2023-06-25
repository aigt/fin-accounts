rootProject.name = "fin-accounts"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
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

include("stubs")
