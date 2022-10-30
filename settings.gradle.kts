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
include(":ios")
include(":web")
include(":wear")
include(":desktop")

// shared
include(":shared:gamelogic")
include(":shared:record:recordDS")
include(":shared:record:recordDSImpl")
include(":shared:commonui")
