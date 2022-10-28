object Deps {
    const val COMPOSE_VERSION = "1.3.0-rc01"
    const val WEAR_COMPOSE_VERSION = "1.1.0-alpha07"

    object Settings {
        const val WOLF_SETTINGS = "com.russhwolf:multiplatform-settings:1.0.0-RC"
        const val WOLF_SETTINGS_COROUTINES =
            "com.russhwolf:multiplatform-settings-coroutines:1.0.0-RC"
    }

    object Coroutine {
        const val mtVersion = "1.6.3-native-mt"
        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$mtVersion"
        const val JS = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$mtVersion"
        const val JVM = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$mtVersion"
    }

    object Koin {
        private const val koin_version = "3.2.2"
        const val CORE = "io.insert-koin:koin-core:$koin_version"
        const val ANDROID = "io.insert-koin:koin-android:$koin_version"
        const val JS = "io.insert-koin:koin-core-js:$koin_version"
        const val JVM = "io.insert-koin:koin-core-jvm:$koin_version"
        const val COMPOSE = "io.insert-koin:koin-androidx-compose:$koin_version"
    }

}