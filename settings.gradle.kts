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
include (":app")

include (":record:recordDS")
include (":record:recordDSImpl")
include (":gamelogic")
include (":web")
include(":shared:viewmodelfabric")
