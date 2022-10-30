plugins {
    kotlin(GradlePlugins.Kotlin.MULTIPLATFORM)
    id(GradlePlugins.Id.ANDROID_LIBRARY)
    id(GradlePlugins.Id.KTLINT)
}

kotlin {
    android()

    jvm()

    initJs {
        nodejs()
    }

    initIos()

    sourceSets {
        val commonMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.Shared.Record.DATA_STORE),
                    project(Modules.Shared.Record.DATA_STORE_IMPL),
                    Deps.Coroutine.CORE,
                    Deps.Koin.CORE
                ).forEach(::implementation)
            }
        }
        val commonTest by getting {
            dependencies {
                arrayOf(
                    kotlin("test"),
                    kotlin("test-common"),
                    kotlin("test-annotations-common"),
                ).forEach(::implementation)
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosMain by creating {
            dependsOn(commonMain)
        }
        val uikitMain by creating {
            dependsOn(iosMain)
        }
        val uikitX64Main by getting {
            dependsOn(uikitMain)
        }
        val uikitArm64Main by getting {
            dependsOn(uikitMain)
        }
    }
}

android {
    namespace = "dmitry.molchanov.gamelogic"
    compileSdk = Config.compileSdk
    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
    }
    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}
