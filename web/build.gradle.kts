plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version ("1.2.0")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "dmitry.molchanov"
version = "1.0"

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                arrayOf(
                    compose.web.core,
                    compose.runtime,
                    project(Modules.Shared.GAME_LOGIC),
                    project(Modules.Shared.Record.DATA_STORE),
                    project(Modules.Shared.Record.DATA_STORE_IMPL),
                    Deps.Koin.JS,
                    Deps.Coroutine.JS
                ).forEach(::implementation)
            }
        }
    }
}
