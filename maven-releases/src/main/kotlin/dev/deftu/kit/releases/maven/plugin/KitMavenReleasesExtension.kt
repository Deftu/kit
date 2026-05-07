package dev.deftu.kit.releases.maven.plugin

import org.gradle.api.provider.Property

abstract class KitMavenReleasesExtension {
    abstract val baseArtifactId: Property<String>
    abstract val artifactSuffix: Property<String>
}