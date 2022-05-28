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
plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    //id("publish-module")

    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        val main by compilations.getting
        val processResources = main.processResourcesTaskName
        (tasks[processResources] as ProcessResources).apply {
            dependsOn(":jni-buffer:assemble")
            from("${project(":jni-buffer").buildDir}/lib/main/release/stripped")
        }

        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnit()
            systemProperty(
                "java.library.path",
                file("${buildDir}/processedResources/jvm/main").absolutePath
            )
        }
    }
    js(IR) {
        moduleName = MetaProject.artifact
        nodejs()
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        val includePath = file("${project(":c-buffer").projectDir}/src/main/public/").absolutePath
        val libraryPathMain = file(project.file("${project(":c-buffer").buildDir}/lib/main/release/")).absolutePath
        val libraryPathTest = file(project.file("${project(":c-buffer").buildDir}/lib/main/debug/")).absolutePath

        val main by compilations.getting

        val cbuffer by main.cinterops.creating {
            defFile(project.file("src/nativeInterop/cinterop/c-buffer.def"))
            compilerOpts("-I$includePath")
            includeDirs.allHeaders(includePath)
            extraOpts("-libraryPath", "$libraryPathMain")
            extraOpts("-libraryPath", "$libraryPathTest")
        }
    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.CInteropProcess::class) {
    dependsOn(":c-buffer:assemble")
}

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
    dokkaSourceSets {
        configureEach {
        }
    }
}

tasks.register<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHugo") {
    dependencies {
        plugins("de.cotech:dokka-hugo-plugin:2.0")
    }
}

fun getExtraString(name: String): String? {
    return project.findProperty(name) as? String ?: System.getenv(name)
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "sonatype"
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