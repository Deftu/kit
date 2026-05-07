# Kit Repositories (`dev.deftu.kit.repositories`)

A modern, centralized repository management plugin for the Kit ecosystem.

Unlike traditional convention plugins that inject `repositories { ... }` into every individual project, `kit-repositories` is a **Settings plugin**. It hooks directly into Gradle's `dependencyResolutionManagement` API to declare your remote Maven servers exactly once, dramatically improving configuration cache performance and build security.

## Installation

> [!IMPORTANT]
> This plugin must be applied in your `settings.gradle.kts` file, *not* your `build.gradle.kts` file.

```kotlin
// settings.gradle.kts
plugins {
    id("dev.deftu.kit.repositories")
}
```
## Features

### Centralized Repository Resolution

By default, applying this plugin automatically configures the following repositories globally for all subprojects:

- `mavenLocal()` (Prioritized first for local development)
- `mavenCentral()`
- Deftu Releases (`https://maven.deftu.dev/releases`)
- Deftu Snapshots (`https://maven.deftu.dev/snapshots`)
- JitPack (`https://jitpack.io`) (De-prioritized to the bottom to prevent timeout delays)