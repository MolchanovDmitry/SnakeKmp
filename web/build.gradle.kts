import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version ("1.2.0")
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
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
                    "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.3-native-mt"

                ).forEach(::implementation)
            }
        }
    }
}

ktlint {
    ignoreFailures.set(false)
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.SARIF)
    }
}
