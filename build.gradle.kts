import java.io.File

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.vanniktechMavenPublish) apply false
}

group = project.findProperty("GROUP")?.toString() ?: "com.msabhi"
version = libs.versions.flywheel.get()

val rootDirPath: String = layout.projectDirectory.asFile.absolutePath
val gradlewName: String = if (System.getProperty("os.name").lowercase().contains("windows")) "gradlew.bat" else "gradlew"

// SPM release preparation
val flywheelSpmDir = project(":flywheel").layout.buildDirectory.dir("spm")
tasks.register<Copy>("copyPackageSwiftToRoot") {
    description = "Copies the generated Package.swift (with binary URL) to the repo root for SPM release."
    group = "spm"
    dependsOn(":flywheel:generatePackageSwift")
    from(flywheelSpmDir.map { it.asFileTree.matching { include("Package.swift") } })
    into(layout.projectDirectory)
}
tasks.register("prepareSpmRelease") {
    description = "Prepares SPM release: builds XCFramework, generates Package.swift, copies to repo root."
    group = "spm"
    dependsOn("copyPackageSwiftToRoot")
}

//----------------------------------------------------------------------------------
// SPM release automation
//----------------------------------------------------------------------------------

val releaseVersion: String = version.toString()

tasks.register<Exec>("releaseSpmGit") {
    group = "release"
    description = "Stages Package.swift, commits if changed, creates tag if not exists"
    dependsOn("prepareSpmRelease")
    workingDir = layout.projectDirectory.asFile
    commandLine(
        "sh", "-c",
        "git add Package.swift && (git diff --staged --quiet || git commit -m \"Release v$releaseVersion for SPM\") && (git rev-parse -q v$releaseVersion >/dev/null 2>&1 || git tag v$releaseVersion)"
    )
}

tasks.register("releaseSpm") {
    group = "release"
    description = "Prepares SPM release: build, git add, commit, tag. Run with -PreleasePush=true to push."
    dependsOn("releaseSpmGit")
}

val releasePushEnabled = project.hasProperty("releasePush")
val releaseGhEnabled = project.hasProperty("releaseGh")

tasks.register<Exec>("releaseSpmPush") {
    group = "release"
    description = "Pushes release commit and tag to origin. Use -PreleasePush=true to run."
    dependsOn("releaseSpm")
    isEnabled = releasePushEnabled
    workingDir = layout.projectDirectory.asFile
    commandLine("sh", "-c", "git push origin HEAD && git push origin v$releaseVersion")
}

tasks.register<Exec>("releaseSpmGh") {
    group = "release"
    description = "Creates GitHub release and uploads zip. Use -PreleaseGh=true to run. Requires gh CLI."
    dependsOn("releaseSpm")
    isEnabled = releaseGhEnabled
    workingDir = layout.projectDirectory.asFile
    // Use relative path; releaseSpm ensures the zip exists. Provider in commandLine is not resolved by Exec.
    commandLine(
        "gh", "release", "create", "v$releaseVersion",
        "flywheel/build/spm/Flywheel.xcframework.zip",
        "--title", "v$releaseVersion"
    )
}

tasks.register("releaseSpmFull") {
    group = "release"
    description = "Full SPM release: build, git add/commit/tag, push, GitHub release. Use -PreleasePush=true, -PreleaseGh=true"
    dependsOn("releaseSpm", "releaseSpmPush", "releaseSpmGh")
}

// Wrapper for IDE: run full SPM release with one click (push + GitHub release)
tasks.register<Exec>("releaseSpmFromIDE") {
    group = "release"
    description = "Full SPM release from IDE: build, git add/commit/tag, push, GitHub release"
    commandLine(
        File(rootDirPath, gradlewName).absolutePath,
        "releaseSpmFull",
        "-PreleasePush=true",
        "-PreleaseGh=true"
    )
    workingDir = File(rootDirPath)
    environment("JAVA_HOME", System.getProperty("java.home"))
}
