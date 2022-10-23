plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()

    js(IR) {
        useCommonJs()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.GAME_LOGIC),
                    project(Modules.Record.DATA_STORE),
                    project(Modules.Record.DATA_STORE_IMPL)
                ).forEach(::implementation)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "dmitry.molchanov.viewmodelfabric"
    compileSdk = Config.compileSdk
    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
    }
}