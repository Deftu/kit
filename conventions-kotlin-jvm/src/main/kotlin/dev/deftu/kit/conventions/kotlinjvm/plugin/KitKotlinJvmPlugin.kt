package dev.deftu.kit.conventions.kotlinjvm.plugin

import dev.deftu.kit.java.plugin.KitJavaExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KitKotlinJvmPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.jvm")
        target.pluginManager.apply("dev.deftu.kit.java")

        val javaExtension = target.extensions.getByType<KitJavaExtension>()
        target.tasks.withType<KotlinCompile>().configureEach { task ->
            task.compilerOptions {
                jvmTarget.set(javaExtension.targetVersion.map { version ->
                    val target = if (version <= 8) "1.$version" else version.toString()
                    JvmTarget.fromTarget(target)
                })
            }
        }
    }
}