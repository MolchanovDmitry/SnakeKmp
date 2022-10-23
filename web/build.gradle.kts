import com.google.wireless.android.sdk.stats.GradleModule

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version ("1.2.0")
}

//group = "dmitry.molchanov"
version = "1.0"

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                arrayOf(
                    compose.web.core,
                    compose.runtime,
                    project(Modules.GAME_LOGIC),
                    project(Modules.Record.DATA_STORE),
                    project(Modules.Record.DATA_STORE_IMPL),
                    "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.3-native-mt"

                ).forEach(::implementation)
            }
        }
    }
}