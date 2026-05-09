package dev.deftu.kit.core

import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.authentication.http.BasicAuthentication
import org.gradle.kotlin.dsl.create

fun MavenArtifactRepository.applyBasicCredentials(username: String, password: String) {
    authentication.create<BasicAuthentication>("basic")
    credentials { creds ->
        creds.username = username
        creds.password = password
    }
}