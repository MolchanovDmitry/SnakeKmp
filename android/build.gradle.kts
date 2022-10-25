import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dmitry.molchanov.snake"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "dmitry.molchanov.snake"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
        resConfigs("en")
    }
    signingConfigs {
        register("release") {
            keyAlias = SigningConfig.keyAlias
            keyPassword = SigningConfig.keyPassword
            storeFile = file(SigningConfig.storeFile)
            storePassword = SigningConfig.storePassword
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.findByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs += arrayOf(
            "-Xno-call-assertions",
            "-Xno-receiver-assertions",
            "-Xno-param-assertions"
        )
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    arrayOf(
        project(Modules.Shared.GAME_LOGIC),
        project(Modules.Shared.Record.DATA_STORE),
        project(Modules.Shared.Record.DATA_STORE_IMPL),
        "androidx.wear.compose:compose-material:${Deps.WEAR_COMPOSE_VERSION}",
        "androidx.activity:activity-compose:1.6.0"
    ).forEach(::implementation)

    implementation("androidx.wear.compose:compose-material:${Deps.WEAR_COMPOSE_VERSION}")
    implementation("androidx.activity:activity-compose:1.6.0")

    testImplementation("androidx.test:core-ktx:1.4.0")
    testImplementation("junit:junit:4.13.2")
}
