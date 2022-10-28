import com.google.wireless.android.sdk.stats.GradleModule

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.Shared.GAME_LOGIC),

                    Deps.Koin.CORE,
                    compose.runtime,
                    compose.foundation,
                    compose.material,
                    compose.materialIconsExtended,
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
    namespace = "dmitry.molchanov.commonui"
    compileSdk = 32
    defaultConfig {
        minSdk = 23
        targetSdk = 32
    }
}