pluginManagement {
    repositories {
        // Snapshots
        mavenLocal()

        // Default repositories
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("dev.deftu.kit.settings") version("0.1.0")
}

listOf(
    "core",
    "repositories",
    "conventions-java",
    "conventions-kotlin-jvm",
    "conventions-kotlin-multiplatform",
    "github-releases",
    "maven-releases",
).forEach { name ->
    file(name).takeIf { !it.exists() }?.mkdirs()
    include(name)
}