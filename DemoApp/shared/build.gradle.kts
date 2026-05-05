@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.compose.compiler)
}

compose.resources {
    packageOfResClass = "framebar.composeapp.generated.resources"
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
    }
    wasmJs {
        browser()
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "DemoAppShared"
            isStatic = true
        }
    }

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
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
        }

        iosMain.dependencies {
        }

    }
}
