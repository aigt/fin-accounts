plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}

    sourceSets {
        val datetimeVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
                implementation(project(":logging:lib:common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

