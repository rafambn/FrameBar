@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(17)

    android {
        namespace = "com.rafambn.framebarcomposeapp.shared"
        compileSdk = 36
        minSdk = 24
    }
    jvm()
    js {
        browser()
        binaries.executable()
    }
    wasmJs {
        browser()
        binaries.executable()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(project(":FrameBar"))
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        val webMain by creating {
            dependsOn(commonMain.get())
        }

        wasmJsMain.get().dependsOn(webMain)
        jsMain.get().apply {
            dependsOn(webMain)
            dependencies {
                implementation(compose.html.core)
            }
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
        }

        iosMain.dependencies {
        }

    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.rafambn.framebar.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}
