import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin(GradlePlugins.Kotlin.MULTIPLATFORM)
    id(GradlePlugins.Id.COMPOSE)
    id(GradlePlugins.Id.KTLINT)
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.Shared.COMMON_UI),
                    project(Modules.Shared.GAME_LOGIC),
                    project(Modules.Shared.Record.DATA_STORE),
                    project(Modules.Shared.Record.DATA_STORE_IMPL),
                    Deps.Koin.JVM,
                    Deps.Coroutine.JVM,
                    compose.desktop.currentOs
                ).forEach(::implementation)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        nativeDistributions {
            val pathToLogo = "src/jvmMain/resources/img/"
            macOS {
                iconFile.set(project.file("${pathToLogo}logo.icns"))
            }
            windows {
                iconFile.set(project.file("${pathToLogo}logo.ico"))
            }
            linux {
                iconFile.set(project.file("${pathToLogo}logo.png"))
            }
        }
        mainClass = "dmitry.molchanov.snake.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "dmitry.molchanov.snake"
            packageVersion = "1.0.0"
        }
    }
}
