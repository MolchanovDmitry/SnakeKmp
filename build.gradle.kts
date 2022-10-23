buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
//plugins {
//    id("com.android.application") version "7.2.0" apply false
//    id("com.android.library") version "7.2.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
//    //kotlin("multiplatform") version "1.7.10" apply false
//    //id("org.jetbrains.compose") version "1.2.0" apply false
//}