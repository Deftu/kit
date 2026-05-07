plugins {
    id("kit.plugin")
    id("dev.deftu.kit.core")
}

dependencies {
    implementation(project(":core"))
}

gradlePlugin {
    plugins {
        register("kitMavenReleases") {
            id = "dev.deftu.kit.maven-releases"
            implementationClass = "dev.deftu.kit.releases.maven.plugin.KitMavenReleasesPlugin"
        }
    }
}