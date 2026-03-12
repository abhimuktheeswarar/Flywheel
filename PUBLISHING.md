# Publishing

Releases are published to [Maven Central](https://central.sonatype.com/) via the [Central Portal](https://central.sonatype.com/), using the [gradle-maven-publish-plugin](https://github.com/vanniktech/gradle-maven-publish-plugin).

## Maven Central

### Prerequisites

- Namespace **com.msabhi** verified at [central.sonatype.com/publishing/namespaces](https://central.sonatype.com/publishing/namespaces).
- **Central Portal user token**: sign in at [central.sonatype.com](https://central.sonatype.com) → profile → **View Account** → **Generate User Token**.
- **GPG signing key**: a GPG key pair with the public key distributed to a key server.

### Credentials

Keep all credentials in the project root `local.properties` (in `.gitignore`):

```properties
SONATYPE_USERNAME=<Central Portal user token username>
SONATYPE_PASSWORD=<Central Portal user token password>
SIGNING_KEY=<armored GPG private key with \n for newlines>
SIGNING_PASSWORD=<GPG passphrase>
```

Signing and upload use these automatically. For **automatic release** (`publishAndReleaseToMavenCentral`), the plugin also needs the username/password as Gradle properties. You can either:

- **From IDE or CLI (recommended):** Run the wrapper task that passes credentials from `local.properties` to the child build via environment variables:
  ```bash
  ./gradlew publishAndReleaseToMavenCentralFromLocal
  ```
  Use this when running from the IDE.

- **Or** run with the init script explicitly:
  ```bash
  ./gradlew publishAndReleaseToMavenCentral -I gradle/init.maven-central.gradle.kts
  ```

Alternatively, put `mavenCentralUsername` and `mavenCentralPassword` in `~/.gradle/gradle.properties` or set `ORG_GRADLE_PROJECT_mavenCentralUsername` and `ORG_GRADLE_PROJECT_mavenCentralPassword` in the environment (then you can run `publishAndReleaseToMavenCentral` directly without the wrapper).

### Commands

```bash
# Publish to Maven Central (automatic release). Uses local.properties via wrapper task.
# Use this from the IDE or terminal:
./gradlew publishAndReleaseToMavenCentralFromLocal

# Upload only — then manually approve at central.sonatype.com/publishing/deployments
./gradlew publishToMavenCentral

# Local testing (skip signing)
./gradlew publishToMavenLocal -PskipSigning=true
```

## Swift Package Manager (SPM)

Apple platform releases use the XCFramework. SPM publishes via GitHub releases.

### Prerequisites

- Xcode with Swift toolchain (for `swift package compute-checksum`)
- [GitHub CLI](https://cli.github.com/) (`gh`) installed on your system for automated GitHub release. After installing, run `gh auth login` to authenticate.

### Automated release (recommended)

```bash
# Build, git add, commit, and tag
./gradlew releaseSpm

# Optional: push to remote and create GitHub release
./gradlew releaseSpmFull -PreleasePush=true -PreleaseGh=true

# From IDE: run releaseSpmFromIDE (under Flywheel or flywheel > release) for full release with one click
```

| Property | Description |
|----------|-------------|
| `-PreleasePush=true` | Push commit and tag to origin |
| `-PreleaseGh=true` | Create GitHub release and upload zip (requires GitHub CLI installed and `gh auth login`) |

### Manual commands

```bash
# Prepare all artifacts only (no git operations)
./gradlew prepareSpmRelease
```

### SPM release steps

1. Run `./gradlew releaseSpm` (or `prepareSpmRelease` + manual git)
2. Push: `./gradlew releaseSpmPush -PreleasePush=true` or `git push origin HEAD && git push origin v<version>`
3. Create GitHub release: `./gradlew releaseSpmGh -PreleaseGh=true` or upload `flywheel/build/spm/Flywheel.xcframework.zip` manually

Consumers add `https://github.com/abhimuktheeswarar/Flywheel.git` and select the version. SwiftPM uses the `Package.swift` at that tag, which points to the zip URL.

### Version alignment

The version is defined in one place: `gradle/libs.versions.toml` (key `flywheel`). Ensure it matches the release tag (e.g. `v1.1.6`).

### Caveats

- Git must be clean before running. If the tag already exists, delete it: `git tag -d v<version>` and `git push origin :refs/tags/v<version>`
- `git commit` fails if there are no changes; run `prepareSpmRelease` first to ensure artifacts exist
