package dev.deftu.kit.core.properties

import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory

object ProjectInfoFactory {
    const val NAME_PROPERTY = "project.name"
    const val VERSION_PROPERTY = "project.version"
    const val GROUP_PROPERTY = "project.group"
    const val DESCRIPTION_PROPERTY = "project.description"

    fun getName(providerFactory: ProviderFactory): Provider<String> {
        return providerFactory.gradleProperty(NAME_PROPERTY)
    }

    fun getVersion(providerFactory: ProviderFactory): Provider<String> {
        return providerFactory.gradleProperty(VERSION_PROPERTY)
    }

    fun getGroup(providerFactory: ProviderFactory): Provider<String> {
        return providerFactory.gradleProperty(GROUP_PROPERTY)
    }

    fun getDescription(providerFactory: ProviderFactory): Provider<String> {
        return providerFactory.gradleProperty(DESCRIPTION_PROPERTY)
    }
}
