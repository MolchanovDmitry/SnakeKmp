plugins {
    kotlin(GradlePlugins.Kotlin.MULTIPLATFORM)
    id(GradlePlugins.Id.ANDROID_LIBRARY)
}

kotlin {
    android()

    js(IR) {
        useCommonJs()
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting
        val androidMain by getting
    }
}

android {
    namespace = "dmitry.molchanov.preference"
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