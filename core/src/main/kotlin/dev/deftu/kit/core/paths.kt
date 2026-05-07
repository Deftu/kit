package dev.deftu.kit.core

import org.gradle.api.Project
import java.io.File

val Project.localGradleCacheDirectory: File
    get() = rootProject.layout.projectDirectory.dir(".gradle").asFile

val Project.globalGradleCacheDirectory: File
    get() = gradle.gradleUserHomeDir
