rootProject.name = "FrameBar"
include(":androidApp")
include(":composeApp")
include(":FrameBar")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
