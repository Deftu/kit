package dev.deftu.kit.core.plugin

import dev.deftu.kit.core.properties.ProjectInfoFactory
import dev.deftu.kit.core.properties.propertyResolver
import dev.deftu.kit.core.vcs.versionControl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

class KitCorePlugin : Plugin<Project> {
    private val pluginVersion: String
        get() = this::class.java.`package`.implementationVersion ?: "unknown"

    override fun apply(target: Project) {
        if (target == target.rootProject) {
            target.logger.lifecycle("Toolbox Bench v$pluginVersion")
        }

        // Lazy-initialize extensions if they have not been created yet
        target.propertyResolver
        target.versionControl

        val extension = target.extensions.create<KitExtension>("kit")
        applyProjectIdentity(target)
        applyVersionMetadata(target, extension)
    }

    private fun applyProjectIdentity(target: Project) {
        val providerFactory = target.providers
        val projectGroup = ProjectInfoFactory.getGroup(providerFactory).orNull
        if (!projectGroup.isNullOrBlank()) {
            target.group = projectGroup
        }

        val projectDescription = ProjectInfoFactory.getDescription(providerFactory).orNull
        if (!projectDescription.isNullOrBlank()) {
            target.description = projectDescription
        }

        target.pluginManager.withPlugin("base") {
            val projectName = ProjectInfoFactory.getName(providerFactory).orNull
            target.configure<BasePluginExtension> {
                if (!projectName.isNullOrBlank()) {
                    archivesName.set(projectName)
                } else {
                    archivesName.set(target.name)
                }
            }
        }
    }

    private fun applyVersionMetadata(target: Project, extension: KitExtension) {
        val providerFactory = target.providers
        val fallbackVersion = target.version.toString()
        val originalVersion = ProjectInfoFactory.getVersion(target.providers)
            .orElse(providerFactory.provider { fallbackVersion })

        extension.versionMetadata.addAll(providerFactory.provider {
            val vcs = target.versionControl
            if (vcs.isPresent && vcs.isAppendable()) {
                listOf(vcs.branch.replace('/', '-'), vcs.shortCommit)
            } else {
                emptyList()
            }
        })

        target.version = object : Any() {
            override fun toString(): String {
                val baseVersion = originalVersion.get()
                val metadata = extension.versionMetadata.get()
                return if (baseVersion != "unspecified" && !baseVersion.endsWith("-RELEASE") && metadata.isNotEmpty()) {
                    "$baseVersion+${metadata.joinToString("-")}"
                } else {
                    baseVersion
                }
            }
        }
    }
}
