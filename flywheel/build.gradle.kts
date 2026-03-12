import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import java.io.File
import java.util.Properties
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktechMavenPublish)
    id("signing")
}

val GROUP: String by project
val VERSION_NAME: String by project

group = GROUP
version = VERSION_NAME

// Read local.properties for signing credentials and Maven Central credentials.
val localProps = Properties()
val localPropsFile = rootProject.file("local.properties")
if (localPropsFile.exists()) {
    localPropsFile.reader().use { reader -> localProps.load(reader) }
}
// Maven Central credentials must be visible as Gradle properties for the vanniktech plugin.
localProps.getProperty("SONATYPE_USERNAME")?.takeIf(String::isNotBlank)?.let { ext.set("mavenCentralUsername", it) }
localProps.getProperty("SONATYPE_PASSWORD")?.takeIf(String::isNotBlank)?.let { ext.set("mavenCentralPassword", it) }

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

mavenPublishing {
    publishToMavenCentral()
    configure(KotlinMultiplatform(
        javadocJar = JavadocJar.Dokka("dokkaGenerateModuleHtml"),
    ))
    pom {
        issueManagement {
            system.set("Github issues")
            url.set("https://github.com/abhimuktheeswarar/Flywheel/issues")
        }
    }
}

signing {
    val signingKey = localProps.getProperty("SIGNING_KEY")
    val signingPassword = localProps.getProperty("SIGNING_PASSWORD")
    val skipSigning = project.findProperty("skipSigning") == "true"
    if (!skipSigning && !signingKey.isNullOrBlank()) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}

// Wrapper so IDE shows a single task that runs publish with credentials from local.properties.
val rootDirPath: String = rootProject.projectDir.absolutePath
val gradlewName: String = if (System.getProperty("os.name").lowercase().contains("windows")) "gradlew.bat" else "gradlew"
tasks.register<Exec>("publishAndReleaseToMavenCentralFromLocal") {
    group = "publishing"
    description = "Publishes to Maven Central with automatic release, loading credentials from local.properties"
    commandLine(
        File(rootDirPath, gradlewName).absolutePath,
        ":flywheel:publishAndReleaseToMavenCentral"
    )
    workingDir = File(rootDirPath)
    // Use current JVM so child build does not inherit invalid JAVA_HOME from IDE
    environment("JAVA_HOME", System.getProperty("java.home"))
    // Pass Maven Central credentials as env vars so Gradle picks them up as project properties.
    // Gradle maps ORG_GRADLE_PROJECT_* to project properties.
    localProps.getProperty("SONATYPE_USERNAME")?.takeIf(String::isNotBlank)?.let {
        environment("ORG_GRADLE_PROJECT_mavenCentralUsername", it)
    }
    localProps.getProperty("SONATYPE_PASSWORD")?.takeIf(String::isNotBlank)?.let {
        environment("ORG_GRADLE_PROJECT_mavenCentralPassword", it)
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
    repoUrl.set(project.findProperty("POM_URL")?.toString() ?: "")
    packageVersion.set(VERSION_NAME)
}

val copyXCFrameworkToRepo by tasks.registering(Copy::class) {
    description = "Copies the release XCFramework into the checked-in xcframework directory for local SPM usage."
    group = "spm"

    dependsOn("assembleFlywheelReleaseXCFramework")

    from(layout.buildDirectory.dir("XCFrameworks/release"))
    into(project.projectDir.resolve("xcframework"))
}
