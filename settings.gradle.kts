pluginManagement {
    plugins {
        kotlin("multiplatform").version("1.7.10")
        //id("org.jetbrains.compose").version("1.2.0")
    }
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenCentral()
        google()
    }
}

//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}

rootProject.name = "Snake"
//include (":app")

//include (":record:recordDS")
//include (":record:recordDSImpl")
//include (":gamelogic")
include (":web")
