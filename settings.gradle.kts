rootProject.name = "fin-accounts"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}

include("fin-accounts-acceptance")
