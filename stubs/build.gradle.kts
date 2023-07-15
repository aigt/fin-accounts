plugins {
    kotlin("multiplatform")
}

val benasherUUIDVersion: String by project

kotlin {
    jvm {}
    linuxX64 {}
    macosX64 {}
    macosArm64 {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation("com.benasher44:uuid:$benasherUUIDVersion")

                implementation(project(":common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
