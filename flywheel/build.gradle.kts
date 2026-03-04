import java.util.Properties
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.dokka)
    id("maven-publish")
    id("signing")
}

val GROUP: String by project
val VERSION_NAME: String by project

val POM_NAME: String by project
val POM_DESCRIPTION: String by project
val POM_URL: String by project
val POM_SCM_URL: String by project
val POM_SCM_CONNECTION: String by project
val POM_SCM_DEV_CONNECTION: String by project
val POM_ISSUES_NAME: String by project
val POM_ISSUES_URL: String by project
val POM_LICENCE_NAME: String by project
val POM_LICENCE_URL: String by project
val POM_LICENCE_DIST: String by project
val POM_DEVELOPER_ID: String by project
val POM_DEVELOPER_NAME: String by project

fun localProperty(key: String): String? {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }
    return localProperties.getProperty(key) ?: System.getenv(key)
}

val POM_DEVELOPER_EMAIL: String = localProperty("POM_DEVELOPER_EMAIL") ?: ""
val SONATYPE_USERNAME: String = localProperty("SONATYPE_USERNAME") ?: ""
val SONATYPE_PASSWORD: String = localProperty("SONATYPE_PASSWORD") ?: ""

group = GROUP
version = VERSION_NAME

val xcf = XCFramework("Flywheel")

kotlin {

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("jvmAndAndroid") {
                withJvm()
                withAndroidTarget()
            }
            group("linuxMingw") {
                withLinuxX64()
                withMingwX64()
            }
        }
    }

    @Suppress("DEPRECATION")
    androidTarget {
        publishAllLibraryVariants()
        publishLibraryVariantsGroupedByFlavor = true
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        testRuns.configureEach {
            executionTask.configure {
                useJUnit()
            }
        }
    }

    js {
        browser()
        nodejs()
    }

    linuxX64()
    mingwX64()

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "flywheel"
            isStatic = true
            xcf.add(this)
        }
    }

    listOf(
        watchosX64(),
        watchosArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "flywheel"
            isStatic = true
            xcf.add(this)
        }
    }

    listOf(
        macosX64(),
        macosArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "flywheel"
            isStatic = true
            xcf.add(this)
        }
    }

    listOf(
        tvosX64(),
        tvosArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "flywheel"
            isStatic = true
            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        val jvmAndAndroidTest by getting {
            dependencies {
                implementation(libs.kotlin.test.junit)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.androidx.test.core)
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.test.runner)
                implementation(libs.androidx.test.rules)
            }
        }
    }
}

android {
    namespace = "com.msabhi.flywheel"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

//----------------------------------------------------------------------------------

val javadocJar = tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.named("dokkaGenerateModuleHtml").map { it.outputs })
}

publishing {

    repositories {
        maven {
            name = "sonatype"
            setUrl {
                val releasesRepoUrl =
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                val snapshotsRepoUrl =
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            }
            credentials {
                username = SONATYPE_USERNAME
                password = SONATYPE_PASSWORD
            }
        }
    }

    publications {

        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set(POM_NAME)
                description.set(POM_DESCRIPTION)
                url.set(POM_URL)
                licenses {
                    license {
                        name.set(POM_LICENCE_NAME)
                        url.set(POM_LICENCE_URL)
                        distribution.set(POM_LICENCE_DIST)
                    }
                }
                issueManagement {
                    system.set(POM_ISSUES_NAME)
                    url.set(POM_ISSUES_URL)
                }
                scm {
                    connection.set(POM_SCM_CONNECTION)
                    url.set(POM_SCM_URL)
                    developerConnection.set(POM_SCM_DEV_CONNECTION)
                }
                developers {
                    developer {
                        id.set(POM_DEVELOPER_ID)
                        name.set(POM_DEVELOPER_NAME)
                        email.set(POM_DEVELOPER_EMAIL)
                    }
                }
            }
        }
    }
}

signing {
    val signingKey = localProperty("SIGNING_KEY")
    val signingPassword = localProperty("SIGNING_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}
