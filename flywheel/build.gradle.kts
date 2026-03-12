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
        watchosDeviceArm64(),
        watchosSimulatorArm64(),
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
        tvosArm64(),
        tvosSimulatorArm64(),
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

val spmDir = layout.buildDirectory.dir("spm")

val assembleFlywheelXCFrameworkForSPM by tasks.registering(Zip::class) {
    description = "Zips the release XCFramework for SPM distribution."
    group = "spm"

    dependsOn("assembleFlywheelReleaseXCFramework")

    val xcframeworkDir = layout.buildDirectory.dir("XCFrameworks/release")
    from(xcframeworkDir)
    destinationDirectory.set(spmDir)
    archiveFileName.set("Flywheel.xcframework.zip")
}

abstract class GeneratePackageSwiftTask : DefaultTask() {

    @get:InputFile
    abstract val zipFile: RegularFileProperty

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @get:Input
    abstract val repoUrl: Property<String>

    @get:Input
    abstract val packageVersion: Property<String>

    @TaskAction
    fun generate() {
        val zip = zipFile.get().asFile
        val process = ProcessBuilder("swift", "package", "compute-checksum", zip.absolutePath)
            .redirectErrorStream(true)
            .start()
        val checksum = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()
        if (exitCode != 0) {
            throw GradleException("swift package compute-checksum failed (exit $exitCode): $checksum")
        }

        val url = repoUrl.get()
        val ver = packageVersion.get()
        val packageSwift = """
            |// swift-tools-version:5.9
            |import PackageDescription
            |
            |let package = Package(
            |    name: "Flywheel",
            |    platforms: [.iOS(.v16), .watchOS(.v9), .tvOS(.v16), .macOS(.v13)],
            |    products: [
            |        .library(
            |            name: "Flywheel",
            |            targets: ["Flywheel"]
            |        ),
            |    ],
            |    targets: [
            |        .binaryTarget(
            |            name: "Flywheel",
            |            url: "$url/releases/download/v$ver/Flywheel.xcframework.zip",
            |            checksum: "$checksum"
            |        ),
            |    ]
            |)
            |""".trimMargin()

        outputFile.get().asFile.writeText(packageSwift)
        logger.lifecycle("Generated Package.swift (checksum: $checksum)")
        logger.lifecycle("  -> ${outputFile.get().asFile.absolutePath}")
    }
}

val generatePackageSwift by tasks.registering(GeneratePackageSwiftTask::class) {
    description = "Generates Package.swift for SPM with checksum of the XCFramework zip."
    group = "spm"

    dependsOn(assembleFlywheelXCFrameworkForSPM)

    zipFile.set(spmDir.map { it.file("Flywheel.xcframework.zip") })
    outputFile.set(spmDir.map { it.file("Package.swift") })
    repoUrl.set(POM_URL)
    packageVersion.set(VERSION_NAME)
}

val copyXCFrameworkToRepo by tasks.registering(Copy::class) {
    description = "Copies the release XCFramework into the checked-in xcframework directory for local SPM usage."
    group = "spm"

    dependsOn("assembleFlywheelReleaseXCFramework")

    from(layout.buildDirectory.dir("XCFrameworks/release"))
    into(project.projectDir.resolve("xcframework"))
}

//----------------------------------------------------------------------------------

val javadocJar = tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    dependsOn(tasks.named("dokkaGenerateModuleHtml"))
    from(layout.buildDirectory.dir("dokka-module/html"))
}

publishing {

    repositories {
        mavenLocal()
        // Sonatype/Central Portal repository is provided by io.github.gradle-nexus.publish-plugin (root build.gradle.kts).
    }

    publications {

        withType<MavenPublication> {
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
        // Attach javadoc only to the root KMP publication to avoid multiple sign tasks writing the same .asc
        named<MavenPublication>("kotlinMultiplatform") {
            artifact(javadocJar)
        }
    }
}

signing {
    val signingKey = localProperty("SIGNING_KEY")
    val signingPassword = localProperty("SIGNING_PASSWORD")
    val skipSigning = project.findProperty("skipSigning") == "true"
    if (!skipSigning && signingKey != null && signingKey.isNotBlank()) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}
