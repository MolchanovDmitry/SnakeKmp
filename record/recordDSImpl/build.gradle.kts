plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()

    js(IR) {
        useCommonJs()
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.Record.DATA_STORE),
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
    }
}

android {
    namespace = "dmitry.molchanov.recorddsimpl"
    compileSdk = Config.compileSdk
    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
    }
}