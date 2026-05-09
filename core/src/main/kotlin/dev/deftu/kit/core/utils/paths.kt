package dev.deftu.kit.core.utils

import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import java.io.File

/// Global Gradle Directories

val Gradle.globalCacheDirectory: File
    get() = gradleUserHomeDir.resolve("caches")

val Project.globalCacheDirectory: File
    get() = gradle.globalCacheDirectory

val Settings.globalCacheDirectory: File
    get() = gradle.globalCacheDirectory