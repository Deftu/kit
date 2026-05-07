package dev.deftu.kit.repositories.plugin

import org.gradle.api.Plugin
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.ArtifactRepository
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.resolve.RepositoriesMode
import org.gradle.api.provider.ProviderFactory

class KitRepositoriesPlugin : Plugin<Settings> {
    override fun apply(target: Settings) {
        val providerFactory = target.providers
        target.dependencyResolutionManagement {
            repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

            repositories {
                mavenCentral()
                mavenLocal()

                maaayven(providerFactory, "general.deftu.releases", "Deftu Releases", "https://maven.deftu.dev/releases/")
                maaayven(providerFactory, "general.deftu.snapshots", "Deftu Snapshots", "https://maven.deftu.dev/snapshots/")

                val minecraftRepositories = providerFactory.gradleProperty("kit.repos.minecraft")
                    .orNull
                    ?.toBooleanStrictOrNull()
                    ?: false
                if (minecraftRepositories) {
                    maaayven(providerFactory, "minecraft.modrinth", "Modrinth", "https://api.modrinth.com/maven/") { onlyForGroups("maven.modrinth") }
                    maaayven(providerFactory, "minecraft.essential", "Essential", "https://repo.essential.gg/repository/maven-public/") { onlyForGroups("gg.essential") }
                    maaayven(providerFactory, "minecraft.kff", "KotlinForForge", "https://thedarkcolour.github.io/KotlinForForge/")
                    maaayven(providerFactory, "minecraft.teamresourceful", "Team Resourceful Releases", "https://maven.teamresourceful.com/repository/maven-releases")
                    maaayven(providerFactory, "minecraft.bawnorton", "Bawnorton Releases", "https://maven.bawnorton.com/releases") { onlyForGroups("com.github.bawnorton") }
                    maaayven(providerFactory, "minecraft.neu", "NotEnoughUpdates Releases", "https://maven.notenoughupdates.org/releases") { onlyForGroups("org.notenoughupdates.moulconfig") }
                    maaayven(providerFactory, "minecraft.spongepowered", "SpongePowered", "https://repo.spongepowered.org/maven/") { onlyForGroups("org.spongepowered") }
                }

                maaayven(providerFactory, "general.jitpack", "JitPack", "https://jitpack.io")
            }
        }
    }

    private fun RepositoryHandler.maaayven(
        providerFactory: ProviderFactory,
        keyPath: String,
        name: String,
        url: String,
        block: ArtifactRepository.() -> Unit = {  }
    ) {
        val isEnabled = providerFactory.gradleProperty("kit.repos.$keyPath").orNull?.toBooleanStrictOrNull() ?: false
        if (!isEnabled) return

        maven {
            setName(name)
            setUrl(url)
            block()
        }
    }

    private fun ArtifactRepository.onlyForGroups(vararg groups: String) {
        content {
            for (group in groups) {
                includeGroup(group)
            }
        }
    }
}
