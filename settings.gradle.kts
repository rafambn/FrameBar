rootProject.name = "FrameBar"
include(":FrameBarComposeApp")
include(":FrameBarXMLApp")
include(":FrameBarCompose")
include(":FrameBarXML")
includeBuild("convention-plugins")

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
