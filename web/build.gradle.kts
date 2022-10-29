plugins {
    kotlin(GradlePlugins.Kotlin.MULTIPLATFORM)
    id(GradlePlugins.Id.COMPOSE)
    id(GradlePlugins.Id.KTLINT)
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
