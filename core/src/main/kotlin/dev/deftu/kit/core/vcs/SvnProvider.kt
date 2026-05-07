package dev.deftu.kit.core.vcs

import dev.deftu.kit.core.exec.execIgnorable
import org.gradle.api.Project

object SvnProvider : VersionControlProvider {
    private val svnInfoCache = mutableMapOf<Project, String>()

    override fun isAvailableTo(project: Project): Boolean {
        return getSvnInfo(project).contains("Repository Root")
    }

    override fun obtainBranch(project: Project): String {
        // SVN branches are not first-class, typically inferred from URL
        val url = obtainRemoteUrl(project)
        return url.substringAfterLast("/branches/", "")
            .ifEmpty { "trunk" }
    }

    override fun obtainCommit(project: Project): String {
        val revisionLine = getSvnInfo(project)
            .lineSequence()
            .firstOrNull { it.startsWith("Revision: ") }
            ?: return ""
        return revisionLine.removePrefix("Revision: ").trim()
    }

    override fun obtainRemoteUrl(project: Project): String {
        val urlLine = getSvnInfo(project)
            .lineSequence()
            .firstOrNull { it.startsWith("URL: ") }
            ?: return ""
        return urlLine.removePrefix("URL: ").trim()
    }

    private fun getSvnInfo(project: Project): String {
        return svnInfoCache.getOrPut(project) {
            project.execIgnorable("svn", "info")
        }
    }
}
