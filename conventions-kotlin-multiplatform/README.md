# Kit Kotlin Multiplatform Conventions (`dev.deftu.kit.conventions.kotlin-multiplatform`)

The ultimate boilerplate-killer for Kotlin Multiplatform (KMP) projects in the Kit ecosystem.

Standard KMP `build.gradle.kts` files are notoriously massive. Developers usually have to manually configure JVM toolchains and declare every native operating system target. `kit-conventions-kotlin-multiplatform` abstracts all of this into a dynamic, property-driven architecture.

## Installation

Apply the plugin in your KMP module's `build.gradle.kts`:
```kotlin
plugins {
    id("dev.deftu.kit.conventions.kotlin-multiplatform")
}
```

*That's it!* You can now immediately start writing code in commonMain, jvmMain, etc.

## Features

### Property-Driven Target Matrix

You no longer need to edit build scripts to add new KMP platforms. This plugin dynamically evaluates your `gradle.properties` file to conditionally generate targets.
```properties
# Enabled by default. Set to false to disable the JVM target.
kit.kmp.jvm=true

# Disabled by default. Set to true to inject all Desktop/Native targets (macOS, Linux, Windows).
kit.kmp.native=false

# Disabled by default. Set to true to inject the JS/Browser target.
kit.kmp.js=false
```