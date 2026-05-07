package dev.deftu.kit.java.plugin

import dev.deftu.kit.core.properties.propertyResolver
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType

class KitJavaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply(JavaPlugin::class)

        val extension = target.extensions.create<KitJavaExtension>("kitJava")
        extension.targetVersion.convention(target.providers.provider {
            val raw = target.propertyResolver.providerOr("kit.java.version", "java.version").orNull
                ?: JavaVersion.current().majorVersion
            KitJavaExtension.parseMajorVersion(raw)
        })

        target.tasks.withType<JavaCompile>().configureEach { task ->
            val version = extension.targetVersion.get()
            task.options.release.set(version)
            task.options.encoding = "UTF-8"
        }
    }
}