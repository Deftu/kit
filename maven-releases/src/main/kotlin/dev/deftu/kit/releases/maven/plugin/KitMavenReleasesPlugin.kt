package dev.deftu.kit.releases.maven.plugin

import dev.deftu.kit.core.properties.propertyResolver
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

class KitMavenReleasesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("maven-publish")
        target.pluginManager.apply("signing")

        val extension = target.extensions.create<KitMavenReleasesExtension>("kitMavenReleases")
        extension.baseArtifactId.convention(target.name)
        extension.artifactSuffix.convention("")

        target.pluginManager.withPlugin("base") {
            val baseExtension = target.extensions.getByType<BasePluginExtension>()
            extension.baseArtifactId.convention(baseExtension.archivesName)
        }

        val publishingUsername = target.propertyResolver.stringOr("kit.maven.username")
        val publishingPassword = target.propertyResolver.stringOr("kit.maven.password")

        val publishingExtension = target.extensions.getByType<PublishingExtension>()
        applyRepositories(target, publishingExtension, publishingUsername, publishingPassword)
        applySigning(target, publishingExtension)
        target.pluginManager.withPlugin("java") { applyJavaPublication(target, publishingExtension, extension) }
        target.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") { applyKotlinMultiplatformPublications(target, publishingExtension, extension) }
    }

    private fun applyRepositories(
        target: Project,
        extension: PublishingExtension,
        publishingUsername: String?,
        publishingPassword: String?
    ) {
        extension.repositories { repositories ->
            repositories.mavenLocal()

            if (publishingUsername != null && publishingPassword != null) {
                listOf(
                    "DeftuReleases" to "releases",
                    "DeftuSnapshots" to "snapshots",
                    "DeftuInternal" to "internal",
                    "DeftuInternalExposed" to "internal-exposed"
                ).forEach { (repoName, path) ->
                    repositories.maven { artifactRepository ->
                        artifactRepository.name = repoName
                        artifactRepository.url = target.uri("https://maven.deftu.dev/$path")
                        artifactRepository.credentials { credentials ->
                            credentials.username = publishingUsername
                            credentials.password = publishingPassword
                        }
                    }
                }
            }
        }
    }

    private fun applyJavaPublication(
        target: Project,
        extension: PublishingExtension,
        kitExtension: KitMavenReleasesExtension
    ) {
        if (target.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) return

        extension.publications.create<MavenPublication>("mavenJava") {
            from(target.components.getByName("java"))

            target.afterEvaluate {
                artifactId = kitExtension.baseArtifactId.get() + kitExtension.artifactSuffix.get()
            }
        }
    }

    private fun applyKotlinMultiplatformPublications(
        target: Project,
        extension: PublishingExtension,
        kitExtension: KitMavenReleasesExtension
    ) {
        target.afterEvaluate {
            extension.publications.withType<MavenPublication>().configureEach { publication ->
                val originalId = publication.artifactId
                val defaultBase = target.name

                val kmpTargetSuffix = if (originalId.startsWith(defaultBase)) {
                    originalId.removePrefix(defaultBase)
                } else {
                    "" // Fallback in case a different plugin completely mangled the ID
                }

                publication.artifactId = kitExtension.baseArtifactId.get() + kitExtension.artifactSuffix.get() + kmpTargetSuffix
            }
        }
    }

    private fun applySigning(target: Project, publishingExtension: PublishingExtension) {
        target.extensions.configure<SigningExtension> {
            val signKey = target.propertyResolver.stringOr("kit.signing.key")
            val signPass = target.propertyResolver.stringOr("kit.signing.password")
            isRequired = signKey != null

            if (signKey != null) {
                useInMemoryPgpKeys(signKey, signPass)
                sign(publishingExtension.publications)
            }
        }
    }
}