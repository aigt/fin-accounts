plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":api-v1-jackson"))
    implementation(project(":common"))
    implementation(project(":stubs"))

    testImplementation(kotlin("test-junit"))
}
