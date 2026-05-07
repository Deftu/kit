package kit

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
}

tasks {
    named<Jar>("jar") {
        from(rootProject.file("LICENSE"))

        manifest {
            attributes["Implementation-Version"] = version
        }
    }
}

configure<PublishingExtension> {
    repositories {
        mavenLocal()
    }
}
