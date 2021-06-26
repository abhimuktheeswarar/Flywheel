import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "com.msabhi"
version = "1.0.0-RC"

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "flywheel"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Coroutines.common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Dependencies.KotlinTest.common)
                implementation(Dependencies.KotlinTest.annotations)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(kotlin("reflect", Versions.kotlin))
                implementation(Dependencies.Coroutines.android)
                implementation(Dependencies.Android.lifecycleRuntime)
                implementation(Dependencies.Android.lifecycleViewModel)
            }

        }

        val androidTest by getting {
            dependencies {
                implementation(Dependencies.KotlinTest.jvm)
                implementation(Dependencies.KotlinTest.junit)
                implementation(Dependencies.Coroutines.test)
                implementation(Dependencies.AndroidTest.core)
                implementation(Dependencies.AndroidTest.junit)
                implementation(Dependencies.AndroidTest.runner)
                implementation(Dependencies.AndroidTest.rules)
            }
        }
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    compileSdkVersion(Versions.Android.compileSdk)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)