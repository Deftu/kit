plugins {
    id("kit.plugin")
    id("dev.deftu.kit.core")
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("kitRepositories") {
            id = "dev.deftu.kit.repositories"
            implementationClass = "dev.deftu.kit.repositories.plugin.KitRepositoriesPlugin"
        }
    }
}