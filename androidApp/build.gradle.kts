plugins {
    id("com.android.application")
    kotlin("android")
}

group = "com.msabhi"
version = "1.0.0-RC"

android {

    compileSdkVersion(Versions.Android.compileSdk)
    buildToolsVersion = Versions.Android.buildTools

    defaultConfig {

        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)

        applicationId = "com.msabhi.androidApp"
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":flywheel"))
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.materialComponents)
    testImplementation(Dependencies.Test.junit)
}