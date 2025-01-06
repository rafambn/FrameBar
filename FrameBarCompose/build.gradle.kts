@file:OptIn(ExperimentalWasmDsl::class, ExperimentalKotlinGradlePluginApi::class)

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "io.github.rafambn"
version = "0.1.1"

kotlin {
    withSourcesJar(publish = false)
    jvmToolchain(11)

    androidTarget { publishLibraryVariants("release") }
    jvm()
    js(IR) {
        browser()
        nodejs()
        binaries.library()
    }
    wasmJs {
        nodejs()
        browser()
        binaries.library()
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
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compileTaskProvider.configure {
            compilerOptions {
                freeCompilerArgs.add("-Xexport-kdoc")
            }
        }
    }
}

android {
    namespace = "io.github.rafambn"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

mavenPublishing {
    coordinates(
        groupId = "io.github.rafambn",
        artifactId = "FrameBar",
        version = "0.1.1"
    )

// Configure POM metadata for the published artifact
    pom {
        name.set("FrameBar")
        description.set("A Kotlin Multiplatform library that implements a custom seekbar.")
        url.set("https://framebar.rafambn.com")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("rafambn")
                name.set("Rafael Mendonca")
                email.set("rafambn@gmail.com")
            }
        }
        scm {
            url.set("https://github.com/rafambn/FrameBar")
        }
    }

// Configure publishing to Maven Central
    publishToMavenCentral(host = SonatypeHost.CENTRAL_PORTAL, automaticRelease = false)

// Enable GPG signing for all publications
    signAllPublications()

    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = true,
            androidVariantsToPublish = listOf("release"),
        )
    )
}