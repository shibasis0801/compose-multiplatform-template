@file:Suppress("OPT_IN_USAGE")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
}

val ktor_version = "2.3.8"
val vertxVersion = "4.5.4"

kotlin {
    androidTarget()

    jvm()

    listOf(
//        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "PodNetwork"
            isStatic = true
        }
    }

    cocoapods {
        version = "1.0"
        summary = "Networking Code here"
        homepage = "www.google.com"

        name = "PodNetwork"
        ios.deploymentTarget = "12"

        framework {
            isStatic = true
            baseName = "PodNetwork"
        }
    }

    sourceSets {
        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                api("io.ktor:ktor-client-core:$ktor_version")
                api("io.ktor:ktor-client-content-negotiation:$ktor_version")
                api("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

            }
        }
        val androidMain by getting {
            dependencies {
                api("io.ktor:ktor-client-okhttp:$ktor_version")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
            }
        }

//        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
//            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                api("io.ktor:ktor-client-darwin:$ktor_version")
//                api("org.jetbrains.kotlinx:kotlinx-coroutines-ui:1.7.3")
            }
        }
        val jvmMain by getting {
            dependencies {
                api("io.ktor:ktor-client-okhttp:$ktor_version")
                api(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
                api("io.vertx:vertx-web-client")
                api("io.vertx:vertx-web")
                api("io.vertx:vertx-lang-kotlin-coroutines")
                api("io.vertx:vertx-lang-kotlin")
                api(kotlin("stdlib-jdk8"))
            }
        }
    }
}

android {
    compileSdk = 34
    namespace = "com.stark.network"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
