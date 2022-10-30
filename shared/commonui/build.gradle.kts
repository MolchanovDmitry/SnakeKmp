plugins {
    kotlin(GradlePlugins.Kotlin.MULTIPLATFORM)
    id(GradlePlugins.Id.ANDROID_LIBRARY)
    id(GradlePlugins.Id.COMPOSE)
    id(GradlePlugins.Id.KTLINT)
}

kotlin {
    android()

    jvm()

    initIos()

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
    namespace = "dmitry.molchanov.commonui"
    compileSdk = 32
    defaultConfig {
        minSdk = 23
        targetSdk = 32
    }
}
