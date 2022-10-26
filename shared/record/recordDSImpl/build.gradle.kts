plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.Shared.Record.DATA_STORE),
                    Deps.Coroutine.CORE,
                    Deps.Settings.WOLF_SETTINGS,
                    Deps.Settings.WOLF_SETTINGS_COROUTINES
                ).forEach(::implementation)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val jsMain by getting
    }
}

android {
    namespace = "dmitry.molchanov.recorddsimpl"
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