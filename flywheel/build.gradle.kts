import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.dokka") version Versions.dokka
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
val POM_DEVELOPER_EMAIL: String = gradleLocalProperties(
    rootDir).getProperty("POM_DEVELOPER_EMAIL",
    System.getenv("POM_DEVELOPER_EMAIL"))

val SONATYPE_USERNAME: String = gradleLocalProperties(
    rootDir).getProperty("SONATYPE_USERNAME",
    System.getenv("SONATYPE_USERNAME"))

val SONATYPE_PASSWORD: String = gradleLocalProperties(
    rootDir).getProperty("SONATYPE_PASSWORD",
    System.getenv("SONATYPE_PASSWORD"))

group = GROUP
version = VERSION_NAME

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    android {
        publishAllLibraryVariants()
        publishLibraryVariantsGroupedByFlavor = true
    }
    js(BOTH) {
        browser()
        nodejs()
    }
    ios()
    watchos()
    tvos()
    macosX64()
    linuxX64()
    mingwX64()
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
        val jvmMain by getting
        val jvmTest by getting {
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
        val androidMain by getting {
            dependsOn(jvmMain)
        }
        val androidTest by getting {
            dependsOn(jvmTest)
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(Dependencies.KotlinTest.js)
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val nativeTest by creating {
            dependsOn(commonTest)
        }
        val appleMain by creating {
            dependsOn(commonMain)
        }
        val appleTest by creating {
            dependsOn(commonTest)
        }
        val iosMain by getting {
            dependsOn(appleMain)
        }
        val iosTest by getting {
            dependsOn(appleTest)
        }
        val watchosMain by getting {
            dependsOn(appleMain)
        }
        val watchosTest by getting {
            dependsOn(appleTest)
        }
        val tvosMain by getting {
            dependsOn(appleMain)
        }
        val tvosTest by getting {
            dependsOn(appleTest)
        }
        val macosX64Main by getting {
            dependsOn(appleMain)
        }
        val macosX64Test by getting {
            dependsOn(appleTest)
        }
        val linuxX64Main by getting {
            dependsOn(nativeMain)
        }
        val linuxX64Test by getting {
            dependsOn(nativeTest)
        }
        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }
        val mingwX64Test by getting {
            dependsOn(nativeTest)
        }
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

//----------------------------------------------------------------------------------

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

//----------------------------------------------------------------------------------

val dokkaOutputDir = "$buildDir/docs"

tasks.dokkaHtml.configure {
    outputDirectory.set(file(dokkaOutputDir))
}

val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
    delete(dokkaOutputDir)
}
val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
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
    val localProps = gradleLocalProperties(rootDir)
    val signingKey = localProps.getProperty("SIGNING_KEY", System.getenv("SIGNING_KEY"))
    val signingPassword = localProps.getProperty(
        "SIGNING_PASSWORD",
        System.getenv("SIGNING_PASSWORD")
    )
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

//----------------------------------------------------------------------------------

val xcFrameworkPath = "xcframework/Flywheel.xcframework"

tasks.create<Delete>("deleteXcFramework") { delete = setOf(xcFrameworkPath) }

val buildXcFramework by tasks.registering {
    dependsOn("deleteXcFramework")
    group = "build"
    val mode = "Release"
    val frameworks = arrayOf(
        "iosArm64",
        "iosX64",
        "watchosArm64",
        "watchosX64",
        "tvosArm64",
        "tvosX64",
        "macosX64")
        .map { kotlin.targets.getByName<KotlinNativeTarget>(it).binaries.getFramework(mode) }
    inputs.property("mode", mode)
    dependsOn(frameworks.map { it.linkTask })
    doLast { buildXcFramework(frameworks) }
}

fun Task.buildXcFramework(frameworks: List<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>) {
    val buildArgs: () -> List<String> = {
        val arguments = mutableListOf("-create-xcframework")
        frameworks.forEach {
            arguments += "-framework"
            arguments += "${it.outputDirectory}/${project.name}.framework"
        }
        arguments += "-output"
        arguments += xcFrameworkPath
        arguments
    }
    exec {
        executable = "xcodebuild"
        args = buildArgs()
    }
}
