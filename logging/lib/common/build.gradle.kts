plugins {
    kotlin("multiplatform")
}

group = "${rootProject.group}.logging.common"
version = rootProject.version

kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}
    macosArm64()

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

tasks.withType<Jar> {
    // Иначе будет конфликтовать с common библиотекой приложения
    archiveBaseName.set("logging-common")
}
