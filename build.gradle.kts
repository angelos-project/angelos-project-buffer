/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */

group = MetaProject.group
version = MetaProject.version

plugins {
    id("org.jetbrains.dokka") version MetaProject.dokkaVersion
    id("org.jetbrains.kotlinx.kover") version MetaProject.koverVersion
    id("io.github.gradle-nexus.publish-plugin") version MetaProject.nexusVersion
}

allprojects {
    repositories {
        mavenCentral()
    }
}


buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(MetaProject.kotlinLibrary)
        classpath(MetaProject.dokkaLibrary)
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokkaCustomMultiModuleOutput"))
}

// apply(plugin = "io.github.gradle-nexus.publish-plugin")
// apply(from = "${rootDir}/publish-root.gradle.kts")

rootProject.ext["signing.keyId"] = null
rootProject.ext["signing.password"] = null
rootProject.ext["signing.secretKeyRingFile"] = null
rootProject.ext["ossrhUsername"] = null
rootProject.ext["ossrhPassword"] = null
rootProject.ext["sonatypeStagingProfileId"] = null

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        java.util.Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        rootProject.ext[name.toString()] = value
    }
} else {
    rootProject.ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    rootProject.ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
    rootProject.ext["sonatypeStagingProfileId"] = System.getenv("SONATYPE_STAGING_PROFILE_ID")
    rootProject.ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    rootProject.ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    rootProject.ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
}

nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId.set(rootProject.ext["sonatypeStagingProfileId"].toString())
            username.set(rootProject.ext["ossrhUsername"].toString())
            password.set(rootProject.ext["ossrhPassword"].toString())
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}