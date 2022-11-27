plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
}

val GROUP: String by project
val VERSION_NAME: String by project

group = GROUP
version = VERSION_NAME

android {

    compileSdk = Versions.Android.compileSdk
    buildToolsVersion = Versions.Android.buildTools

    defaultConfig {

        minSdk = Versions.Android.minSdk
        targetSdk = Versions.Android.targetSdk

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":flywheel"))
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.activityKtx)
    implementation(Dependencies.Android.fragmentKtx)
    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.materialComponents)
    testImplementation(Dependencies.Test.junit)
}