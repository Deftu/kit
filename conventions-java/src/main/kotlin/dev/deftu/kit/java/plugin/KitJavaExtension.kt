package dev.deftu.kit.java.plugin

import org.gradle.api.provider.Property

abstract class KitJavaExtension {
    companion object {
        fun parseMajorVersion(raw: String): Int {
            val formatted = raw.removePrefix("1.").substringBefore(".")
            val stripped = formatted.filter(Char::isDigit)
            return stripped.toIntOrNull() ?: throw IllegalArgumentException("Invalid Java version: $raw")
        }
    }

    abstract val targetVersion: Property<Int>
}