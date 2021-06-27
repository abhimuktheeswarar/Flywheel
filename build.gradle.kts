buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        classpath(Dependencies.androidGradlePlugin)
    }
}

group = "com.msabhi"
version = "1.0.0-RC"

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}