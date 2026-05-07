package dev.deftu.kit.core.vcs

import dev.deftu.kit.core.exec.execIgnorable
import org.gradle.api.Project

object HgProvider : VersionControlProvider {
    override fun isAvailableTo(project: Project): Boolean {
        return project.execIgnorable("hg", "root").isNotBlank()
    }

    override fun obtainBranch(project: Project): String {
        return project.execIgnorable("hg", "branch").trim()
    }

    override fun obtainCommit(project: Project): String {
        return project.execIgnorable("hg", "id", "-i").trim()
    }

    override fun obtainRemoteUrl(project: Project): String {
        return project.execIgnorable("hg", "paths", "default").trim()
    }
}
