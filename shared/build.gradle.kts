plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:2.2.4")
                //implementation("io.ktor:ktor-server-netty:+")
//                implementation("io.ktor:ktor-server-html-builder:2.2.4")
//                implementation("ch.qos.logback:logback-classic:1.4.6")
                implementation("io.ktor:ktor-client-cio:2.2.4")
                implementation("io.ktor:ktor-client-logging:2.2.4")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("com.squareup.okio:okio:3.3.0")
                runtimeOnly("com.squareup.okio:okio-multiplatform:3.0.0-alpha.9")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
//                implementation("io.ktor:ktor-server-test-host:2.2.4")
//                implementation("org.jetbrains.kotlin:kotlin-test")
            }
        }
        val androidMain by getting
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("com.squareup.okio:okio:3.3.0")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.skanderjabouzi.speedtest"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}