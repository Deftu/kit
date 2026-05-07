package dev.deftu.kit.conventions.kotlinmultiplatform.plugin

import dev.deftu.kit.core.properties.propertyResolver
import dev.deftu.kit.java.plugin.KitJavaExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KitKotlinMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.multiplatform")

        val propertyResolver = target.propertyResolver
        val enableJvm = propertyResolver.booleanOr("kit.kmp.jvm", default = true)
        val enableJs = propertyResolver.booleanOr("kit.kmp.js", default = false)
        val enableWasm = propertyResolver.booleanOr("kit.kmp.wasm", default = false)
        val enableNative = propertyResolver.booleanOr("kit.kmp.native", default = false)

        if (enableJvm) {
            target.pluginManager.apply("dev.deftu.kit.java")
        }

        target.configure<KotlinMultiplatformExtension> {
            if (enableJvm) {
                val javaExtension = target.extensions.getByType<KitJavaExtension>()

                jvm {
                    compilations.configureEach {
                        compilerOptions {
                            jvmTarget.set(javaExtension.targetVersion.map {
                                val targetString = if (it <= 8) "1.$it" else it.toString()
                                JvmTarget.fromTarget(targetString)
                            })
                        }
                    }
                }
            }

            if (enableJs) {
                val enableBrowser = propertyResolver.booleanOr("kit.kmp.js.browser", default = true)
                val enableNode = propertyResolver.booleanOr("kit.kmp.js.node", default = true)
                js(IR) {
                    if (enableBrowser) browser()
                    if (enableNode) nodejs()
                }
            }

            if (enableWasm) {
                val enableBrowser = propertyResolver.booleanOr("kit.kmp.wasm.browser", default = true)
                val enableNode = propertyResolver.booleanOr("kit.kmp.wasm.node", default = true)
                wasmJs {
                    if (enableBrowser) browser()
                    if (enableNode) nodejs()
                }
            }

            if (enableNative) {
                linuxX64()
                macosX64()
                macosArm64()
                mingwX64()
            }
        }
    }
}