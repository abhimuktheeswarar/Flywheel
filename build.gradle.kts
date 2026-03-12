plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.vanniktechMavenPublish) apply false
}

group = project.findProperty("GROUP")?.toString() ?: "com.msabhi"
version = project.findProperty("VERSION_NAME")?.toString() ?: "1.0.0"
