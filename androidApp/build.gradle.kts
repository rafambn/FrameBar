import com.android.build.api.dsl.ApplicationExtension

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

extensions.configure<ApplicationExtension>("android") {
    namespace = "com.rafambn.framebarcomposeapp"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        targetSdk = 36

        applicationId = "com.rafambn.framebar"
        versionCode = 1
        versionName = "1.0.0"
    }
}

dependencies {
    implementation(project(":composeApp"))
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.appcompat)
    implementation(libs.compose.uitooling)
}
