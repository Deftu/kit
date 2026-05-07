package dev.deftu.kit.core.vcs

import dev.deftu.kit.core.exec.execIgnorable
import dev.deftu.kit.core.properties.propertyResolver
import org.gradle.api.Project

object GitProvider : VersionControlProvider {
    private val BRANCH_VARIABLES = setOf("GIT_BRANCH", "GITHUB_REF_NAME")
    private val COMMIT_VARIABLES = setOf("GIT_COMMIT", "GITHUB_SHA")

    override fun isAvailableTo(project: Project): Boolean {
        if (
            project.propertyResolver.has(*BRANCH_VARIABLES.toTypedArray()) &&
            project.propertyResolver.has(*COMMIT_VARIABLES.toTypedArray())
        ) {
            return true
        }

        return project.execIgnorable("git", "rev-parse", "--is-inside-work-tree").toBooleanStrictOrNull() == true
    }

    override fun obtainBranch(project: Project): String {
        project.propertyResolver.stringOrThrow(*BRANCH_VARIABLES.toTypedArray(), default = "").takeIf(String::isNotBlank)?.let { branch ->
            return branch
        }

        return project.execIgnorable("git", "rev-parse", "--abbrev-ref", "HEAD").trim()
    }

    override fun obtainCommit(project: Project): String {
        project.propertyResolver.stringOrThrow(*COMMIT_VARIABLES.toTypedArray(), default = "").takeIf(String::isNotBlank)?.let { commit ->
            return commit
        }

        return project.execIgnorable("git", "rev-parse", "HEAD").trim()
    }

    override fun obtainRemoteUrl(project: Project): String {
        return project.execIgnorable("git", "config", "--get", "remote.origin.url").trim()
    }
}
