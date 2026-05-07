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
        register("kitConventionsKotlinJvm") {
            id = "dev.deftu.kit.conventions.kotlin-jvm"
            implementationClass = "dev.deftu.kit.conventions.kotlinjvm.plugin.KitKotlinJvmPlugin"
        }
    }
}