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
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*

plugins {
    `maven-publish`
    signing
}

fun getExtraString(name: String): String? {
    return project.findProperty(name) as? String ?: System.getenv(name)
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                credentials {
                    username = rootProject.ext["ossrhUsername"].toString()
                    password = rootProject.ext["ossrhPassword"].toString()
                }
            }
        }
        publications {
            create<MavenPublication>("maven") {
                val javadocJar by project.tasks.creating(org.gradle.jvm.tasks.Jar::class) {
                    archiveClassifier.set("javadoc")
                }
                groupId = MetaProject.group
                artifactId = MetaProject.artifact
                version = MetaProject.version
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
}

signing {
    sign(publishing.publications)
}