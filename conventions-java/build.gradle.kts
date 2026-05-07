plugins {
    id("kit.plugin")
    id("dev.deftu.kit.core")
}

dependencies {
    implementation(project(":core"))
}

gradlePlugin {
    plugins {
        register("kitConventionsJava") {
            id = "dev.deftu.kit.conventions.java"
            implementationClass = "dev.deftu.kit.conventions.java.plugin.KitJavaPlugin"
        }
    }
}