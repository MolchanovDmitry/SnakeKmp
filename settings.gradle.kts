pluginManagement {
    plugins {
        kotlin("multiplatform").version("1.7.10")
    }
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenCentral()
        google()
    }
}

rootProject.name = "Snake"

// platforms
include(":web")
include(":android")

// shared
include(":shared:gamelogic")
include(":shared:viewmodelfabric")
include(":shared:record:recordDS")
include(":shared:record:recordDSImpl")
