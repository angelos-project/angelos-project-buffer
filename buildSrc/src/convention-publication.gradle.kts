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

import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.signing
import java.util.*

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("io.github.gradle-nexus.publish-plugin")
    `maven-publish`
    signing
}

/*
// Stub secrets to let the project sync and build without the publication values set up
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI
val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
} else {
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

fun getExtraString(name: String) = ext[name]?.toString()

publishing {
    // Configure maven central repository
    repositories {
        maven {
            name = "sonatype"

            // Repository URL for snapshot deployment and download access:
            //setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")

            //Repository URL for release deployment, no download access! :
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    publications.withType<MavenPublication> {

        artifact(javadocJar)

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("Buffer for Angelos Project™")
            description.set("A data-buffer made to be used without old legacy, in a modern asynchronous environment. With all necessary fundaments.")
            url.set("https://angelos-project.com")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("kristoffer-paulsson")
                    name.set("Kristoffer Paulsson")
                    email.set("kristoffer@angelos-project.com")
                }
            }
            scm {
                url.set("https://angproj.org/angelos-project-buffer")
            }
        }
    }
}

// Signing artifacts. Signing.* extra properties values will be used

signing {
    sign(publishing.publications)
}*/

fun getExtraString(name: String): String? {
    return project.findProperty(name) as? String ?: System.getenv(name)
}

ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null
ext["sonatypeStagingProfileId"] = null

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
} else {
    ext["ossrhUsername"] = getExtraString("OSSRH_USERNAME")
    ext["ossrhPassword"] = getExtraString("OSSRH_PASSWORD")
    ext["sonatypeStagingProfileId"] = getExtraString("SONATYPE_STAGING_PROFILE_ID")
    ext["signing.keyId"] = getExtraString("SIGNING_KEY_ID")
    ext["signing.password"] = getExtraString("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = getExtraString("SIGNING_SECRET_KEY_RING_FILE")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            /*val sourcesJar by project.tasks.creating(org.gradle.jvm.tasks.Jar::class) {
                archiveClassifier.set("sources")
                from(project.sourceSets.main.get().allSource)
            }*/
            val javadocJar by project.tasks.creating(org.gradle.jvm.tasks.Jar::class) {
                archiveClassifier.set("javadoc")
            }
            groupId = MetaProject.group
            artifactId = MetaProject.artifact
            version = MetaProject.version
            //artifact(sourcesJar)
            artifact(javadocJar)
        }
    }

    publications.withType(MavenPublication::class) {
        pom {
            if (!name.isPresent) {
                name.set(artifactId)
            }
            description.set(MetaProject.mavenDescription)
            url.set(MetaProject.homeRepo)
            licenses {
                license {
                    name.set(MetaProject.licenceName)
                    url.set(MetaProject.licenceUrl)
                    distribution.set("repo")
                }
            }
            developers {
                developer {
                    id.set(MetaDevelopers.devID)
                    name.set(MetaDevelopers.devName)
                    email.set(MetaDevelopers.devEmail)
                }
                organization {
                    name.set(MetaDevelopers.devOrg)
                    url.set(MetaDevelopers.devOrgUrl)
                }
            }
            scm {
                url.set(MetaProject.mavenScmUrl)
                connection.set(MetaProject.mavenScmConnection)
                developerConnection.set(MetaProject.mavenScmDeveloperConnection)
            }
            issueManagement {
                url.set(MetaProject.issueManagement)
            }
        }
    }
}

signing {
    if(!ext["signing.secretKeyRingFile"].toString().isNullOrBlank()) {
        useInMemoryPgpKeys(
            ext["signing.secretKeyRingFile"].toString(),
            ext["signing.keyId"].toString(),
            ext["signing.password"].toString()
        )
        sign(publishing.publications)
    }
}

nexusPublishing {
    repositories {
        sonatype {
            if (
                !ext["ossrhUsername"].toString().isNullOrBlank() &&
                !ext["ossrhPassword"].toString().isNullOrBlank() &&
                !ext["sonatypeStagingProfileId"].toString().isNullOrBlank()
            ) {
                stagingProfileId.set(ext["sonatypeStagingProfileId"].toString())
                username.set(ext["ossrhUsername"].toString())
                password.set(ext["ossrhPassword"].toString())
            }
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}