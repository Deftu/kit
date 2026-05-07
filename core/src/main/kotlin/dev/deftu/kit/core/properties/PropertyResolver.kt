package dev.deftu.kit.core.properties

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.findByType

val Project.propertyResolver: PropertyResolver
    get() = PropertyResolver.get(this)

class PropertyResolver(private val project: Project) {
    companion object {
        private const val EXCEPTION_MESSAGE = "No value found for key \"%s\". Set it in gradle.properties, environment variables or in the system properties."

        @JvmStatic
        fun get(project: Project): PropertyResolver {
            project.extensions.findByType<PropertyResolver>()?.let { return it }

            val resolver = PropertyResolver(project)
            project.extensions.add("propRes", resolver)
            return resolver
        }

        private fun constructExceptionMessage(keys: Array<out String>): String {
            return EXCEPTION_MESSAGE.format(keys.joinToString(", "))
        }
    }

    fun providerOr(vararg keys: String): Provider<String> {
        val providers = project.providers
        var currentProvider: Provider<String> = providers.provider { null }

        for (key in keys) {
            currentProvider = currentProvider
                .orElse(providers.gradleProperty(key)
                .orElse(providers.systemProperty(key))
                .orElse(providers.environmentVariable(key))
                .orElse(providers.environmentVariable(key.uppercase().replace('.', '_')))
                .orElse(providers.environmentVariable(key.lowercase().replace('.', '_'))))
        }

        return currentProvider
    }

    fun has(vararg keys: String): Boolean {
        return providerOr(*keys).isPresent
    }

    fun stringOr(
        vararg keys: String,
        default: String? = null,
    ): String? {
        return providerOr(*keys).orNull ?: default
    }

    fun stringOrThrow(
        vararg keys: String,
        default: String? = null,
    ): String {
        return stringOr(*keys, default = default)
            ?: throw IllegalArgumentException(constructExceptionMessage(keys))
    }

    fun booleanOr(
        vararg keys: String,
        default: Boolean? = null,
    ): Boolean {
        return stringOr(*keys, default = default?.toString())?.toBooleanStrictOrNull() ?: false
    }

    fun booleanOrThrow(
        vararg keys: String,
        default: Boolean? = null,
    ): Boolean {
        return stringOr(*keys, default = default?.toString())?.toBooleanStrictOrNull()
            ?: throw IllegalArgumentException("Property [${keys.joinToString(", ")}] exists but is not a valid boolean.")
    }

    fun intOr(
        vararg keys: String,
        default: Int? = null,
    ): Int {
        return stringOr(*keys, default = default?.toString())?.toIntOrNull() ?: 0
    }

    fun intOrThrow(
        vararg keys: String,
        default: Int? = null,
    ): Int {
        return stringOr(*keys, default = default?.toString())?.toInt()
            ?: throw IllegalArgumentException(constructExceptionMessage(keys))
    }

    fun doubleOr(
        vararg keys: String,
        default: Double? = null,
    ): Double {
        return stringOr(*keys, default = default?.toString())?.toDoubleOrNull() ?: 0.0
    }

    fun doubleOrThrow(
        vararg keys: String,
        default: Double? = null,
    ): Double {
        return stringOr(*keys, default = default?.toString())?.toDouble()
            ?: throw IllegalArgumentException(constructExceptionMessage(keys))
    }
}
