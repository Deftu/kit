package dev.deftu.kit.core.vcs

import org.gradle.api.Project

interface VersionControlProvider {
    object Unavailable : VersionControlProvider {
        override fun isAvailableTo(project: Project): Boolean = false
        override fun obtainBranch(project: Project): String = ""
        override fun obtainCommit(project: Project): String = ""
        override fun obtainRemoteUrl(project: Project): String = ""
    }

    fun isAvailableTo(project: Project): Boolean
    fun obtainBranch(project: Project): String
    fun obtainCommit(project: Project): String
    fun obtainRemoteUrl(project: Project): String
}
