rootProject.name = "FrameBar"
include(":FrameBar")
include(":DemoApp:shared")
include(":DemoApp:androidApp")
include(":DemoApp:desktopApp")
include(":DemoApp:webApp")

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
