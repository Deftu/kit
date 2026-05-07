# Kit Java Conventions (`dev.deftu.kit.conventions.java`)

A modern convention plugin that standardizes Java compilation across the Kit ecosystem.

## Installation

Apply the plugin in your `build.gradle.kts`:
```kotlin
plugins {
    id("dev.deftu.kit.conventions.java")
}
```

## Features

### Centralized Version Management

You can control the Java version for an entire monorepo with a single line in your `gradle.properties`:
```properties
kit.java.version=21
```