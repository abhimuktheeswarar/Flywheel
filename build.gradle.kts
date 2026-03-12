plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.dokka) apply false
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = project.findProperty("GROUP")?.toString() ?: "com.msabhi"
version = project.findProperty("VERSION_NAME")?.toString() ?: "1.0.0"

fun rootLocalProperty(key: String): String? {
    val file = rootProject.file("local.properties")
    if (!file.exists()) return null
    val props = java.util.Properties()
    file.reader().use { props.load(it) }
    return props.getProperty(key) ?: System.getenv(key)
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            username.set(
                rootLocalProperty("SONATYPE_USERNAME")
                    ?: project.findProperty("sonatypeUsername")?.toString()
                    ?: ""
            )
            password.set(
                rootLocalProperty("SONATYPE_PASSWORD")
                    ?: project.findProperty("sonatypePassword")?.toString()
                    ?: ""
            )
        }
    }
}
