// Loads Maven Central credentials from local.properties so the vanniktech
// plugin's automatic-release step can use them. Apply with:
//   ./gradlew publishAndReleaseToMavenCentral -I gradle/init.maven-central.gradle.kts
gradle.beforeSettings {
    val rootDir = gradle.startParameter.currentDir
    val localPropsFile = rootDir.resolve("local.properties")
    if (localPropsFile.exists()) {
        val props = java.util.Properties()
        localPropsFile.reader().use { props.load(it) }
        mapOf(
            "SONATYPE_USERNAME" to "mavenCentralUsername",
            "SONATYPE_PASSWORD" to "mavenCentralPassword",
        ).forEach { (localKey, gradleKey) ->
            props.getProperty(localKey)?.takeIf { it.isNotBlank() }?.let { value ->
                System.setProperty("org.gradle.project.$gradleKey", value)
            }
        }
    }
}
