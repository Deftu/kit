# Kit Kotlin JVM Conventions (`dev.deftu.kit.conventions.kotlin-jvm`)

A highly optimized convention plugin that seamlessly aligns Kotlin and Java compilation across the Kit ecosystem.

In standard Gradle builds, maintaining a mixed Java/Kotlin codebase requires manually configuring both the `java` extension and the `kotlin` compiler options to ensure their target bytecodes match. `kit-conventions-kotlin-jvm` eliminates this boilerplate by strictly coupling the Kotlin compiler to the central `KitJavaExtension`, ensuring perfect sync.

## Installation

Apply the plugin in your `build.gradle.kts`:

```kotlin
plugins {
    // Automatically applies org.jetbrains.kotlin.jvm and dev.deftu.kit.java under the hood
    id("dev.deftu.kit.conventions.kotlin-jvm")
}
```

## Features

### Centralized Version Management

You can control the Java version for an entire monorepo with a single line in your `gradle.properties`:
```properties
kit.java.version=21
```