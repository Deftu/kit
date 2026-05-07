# Kit Core (`dev.deftu.kit.core`)

The foundational plugin for the Kit Gradle ecosystem.

`kit-core` does not configure any specific build tools or compile steps. Instead, it provides the essential, Configuration Cache-safe utilities and architectural backbone that all other Kit plugins rely on.

## Installation

> [!NOTE]
> It's not absolutely necessary to apply `dev.deftu.kit.core` in your `settings.gradle.kts` file, but doing so automatically applies your `project.name` property to the root project, which is a common convention in the Kit ecosystem. If you choose not to apply it in `settings.gradle.kts`, make sure to set `rootProject.name` manually.

Apply the plugin in your `settings.gradle.kts`:
```kotlin
// settings.gradle.kts
plugins {
    id("dev.deftu.kit.core")
}
```

> [!NOTE]
> Most downstream plugins (like `dev.deftu.kit.conventions.java`) apply this core plugin automatically under the hood.
> It's still recommended to apply it as the first plugin in your build script to ensure all core features are available for subsequent plugins.

Then, apply the plugin in your `build.gradle.kts`:
```kotlin
plugins {
    id("dev.deftu.kit.core")
}
```