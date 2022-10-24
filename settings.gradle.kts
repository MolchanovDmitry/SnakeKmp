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
include (":web")
include (":android")

include (":record:recordDS")
include (":record:recordDSImpl")
include (":gamelogic")
include(":shared:viewmodelfabric")
