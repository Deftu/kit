package dev.deftu.kit.core.plugin

import dev.deftu.kit.core.properties.ProjectInfoFactory
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class KitSettingsPlugin : Plugin<Settings> {
    override fun apply(target: Settings) {
        val projectName = ProjectInfoFactory.getName(target.providers).orNull
        if (!projectName.isNullOrBlank()) {
            target.rootProject.name = projectName
        }
    }
}
