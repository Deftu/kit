# Kit Maven Releases (`dev.deftu.kit.maven-releases`)

A highly modular publishing convention plugin for the Kit ecosystem.

`kit-maven-releases` completely abstracts the boilerplate of the standard `maven-publish` and `signing` plugins. It intelligently handles standard Java libraries and multi-target Kotlin Multiplatform (KMP) modules without hardcoding any domain-specific logic.

## Installation

Apply the plugin in your `build.gradle.kts`:
```kotlin
plugins {
    id("dev.deftu.kit.maven-releases")
}
```

## Environment Variables & Properties

The plugin uses the kit-core property resolver to safely and lazily read the following secrets.
You can provide these in `gradle.properties` (or better yet, your global `gradle.properties`) or as environment variables (e.g., `KIT_MAVEN_USERNAME`).
```properties
# Maven Credentials (If missing, remote publishing is disabled gracefully)
kit.maven.username=my-user
kit.maven.password=my-token

# PGP Signing Keys (If missing, signing is skipped gracefully)
# The key should be a Base64 or ASCII-armored string of your private key
kit.signing.key=-----BEGIN PGP PRIVATE KEY BLOCK-----...
kit.signing.password=my-key-passphrase
```