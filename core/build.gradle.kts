plugins {
    id("kit.plugin")
    id("dev.deftu.kit.core")
}

gradlePlugin {
    plugins {
        register("kitCore") {
            id = "dev.deftu.kit.core"
            implementationClass = "dev.deftu.kit.core.plugin.KitCorePlugin"
        }

        register("kitSettings") {
            id = "dev.deftu.kit.settings"
            implementationClass = "dev.deftu.kit.core.plugin.KitSettingsPlugin"
        }
    }
}