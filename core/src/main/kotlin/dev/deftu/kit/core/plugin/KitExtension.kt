package dev.deftu.kit.core.plugin

import org.gradle.api.provider.ListProperty

abstract class KitExtension {
    abstract val versionMetadata: ListProperty<String>
}
