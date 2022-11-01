plugins {
    kotlin(GradlePlugins.Kotlin.MULTIPLATFORM)
    id(GradlePlugins.Id.COMPOSE)
    id(GradlePlugins.Id.KTLINT)
}

kotlin {

    initIos()

    sourceSets {
        val commonMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.Shared.COMMON_UI),

                    compose.ui,
                    compose.foundation,
                    compose.material,
                    compose.runtime,
                    Deps.Koin.CORE,
                ).forEach(::implementation)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
        }
        val uikitMain by creating {
            dependsOn(iosMain)
        }
        val uikitX64Main by getting {
            dependsOn(uikitMain)
        }
        val uikitArm64Main by getting {
            dependsOn(uikitMain)
        }
    }
}

compose.experimental {
    uikit.application {
        bundleIdPrefix = "dmitry.molchanov.snake"
        projectName = "Snake"
        deployConfigurations {
            simulator("IPhone13") {
                // Usage: ./gradlew iosDeployIPhone13Debug
                device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_PRO
            }
            simulator("IPadUI") {
                // Usage: ./gradlew iosDeployIPadUIDebug
                device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPAD_MINI_6th_Gen
            }
        }
    }
}
