plugins {
    id("org.jetbrains.intellij") version "1.9.0"
    java
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("idea")
}

group = "dmitry.molchanov"
version = "1.0"

dependencies {
    arrayOf(
        project(Modules.Shared.COMMON_UI),
        project(Modules.Shared.GAME_LOGIC),
        project(Modules.Shared.Record.DATA_STORE),
        project(Modules.Shared.Record.DATA_STORE_IMPL),
        Deps.Koin.JVM,
        Deps.Coroutine.JVM,
        compose.desktop.currentOs,
    ).forEach(::implementation)
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.3")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
