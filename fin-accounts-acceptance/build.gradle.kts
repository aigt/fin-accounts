plugins {
    kotlin("jvm")
    application
}

dependencies {
    testImplementation(kotlin("test"))
}

sourceSets {
    val main by getting
    val test by getting
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}
