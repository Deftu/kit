plugins {
    id("kit.plugin")
    id("dev.deftu.kit.core")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":conventions-java"))
    implementation(kotlin("gradle-plugin"))
}

gradlePlugin {
    plugins {
        register("kitConventionsKotlinMultiplatform") {
            id = "dev.deftu.kit.conventions.kotlin-multiplatform"
            implementationClass = "dev.deftu.kit.conventions.kotlinmultiplatform.plugin.KitKotlinMultiplatformPlugin"
        }
    }
}