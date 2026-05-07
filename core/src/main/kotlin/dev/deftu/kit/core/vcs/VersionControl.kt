package dev.deftu.kit.core.vcs

import dev.deftu.kit.core.properties.propertyResolver
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import kotlin.collections.iterator

val Project.versionControl: VersionControl
    get() = VersionControl.get(this)

data class VersionControl(
    val isPresent: Boolean,
    private val provider: VersionControlProvider,
    private val project: Project,
) {
    companion object {
        private val providers = mapOf(
            "git" to GitProvider,
            "hg" to HgProvider,
            "svn" to SvnProvider,
        )

        @JvmStatic
        fun get(project: Project): VersionControl {
            // Return cached instance if available
            project.extensions.findByType<VersionControl>()?.let { return it }

            val container = identifyProvider(project) ?: return VersionControl(isPresent = false, provider = VersionControlProvider.Unavailable, project = project)
            return VersionControl(
                isPresent = true,
                provider = container,
                project = project,
            ).also {
                project.extensions.add("versionControl", it)
            }
        }

        private fun identifyProvider(project: Project): VersionControlProvider? {
            val forcedProvider = project.propertyResolver.stringOr("vcs.provider")
            if (!forcedProvider.isNullOrBlank()) {
                return providers[forcedProvider]
            }

            for ((name, provider) in providers) {
                if (provider.isAvailableTo(project)) {
                    project.logger.debug("Automatically identified VCS provider: $name")
                    return provider
                }
            }

            return null
        }
    }

    val branch by lazy { provider.obtainBranch(project) }
    val commit by lazy { provider.obtainCommit(project) }
    val remoteUrl by lazy { provider.obtainRemoteUrl(project) }

    val shortCommit by lazy { commit.take(7) }

    fun isAppendable(): Boolean {
        return isPresent && project.propertyResolver.booleanOr("vcs.appendable", default = false)
    }
}
