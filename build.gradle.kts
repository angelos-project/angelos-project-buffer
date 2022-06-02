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
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.14"
    //id("com.github.kt3k.coveralls") version "2.12.0"
    id("project-publish-setup")
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

/*coveralls {
    jacocoReportPath = "buffer/build/reports/kover/project-xml/report.xml"
    sendToCoveralls = true
    sourceDirs = listOf(
        "$buildDir/buffer/src/commonMain/kotlin/",
        "$buildDir/buffer/src/jvmMain/kotlin/",
        "$buildDir/buffer/src/jsMain/kotlin/",
        "$buildDir/buffer/src/nativeMain/kotlin/",
    )
}*/

coverallsJacoco {
    reportPath = "$buildDir/build/reports/kover/report.xml"

    reportSourceSets = listOf(
        File("$buildDir/buffer/src/commonMain/kotlin/"),
        File("$buildDir/buffer/src/jvmMain/kotlin/"),
        File("$buildDir/buffer/src/jsMain/kotlin/"),
        File("$buildDir/buffer/src/nativeMain/kotlin/"),
    )
    //apiEndpoint = "" // default: https://coveralls.io/api/v1/jobs

    //dryRun = false // default: false
    //coverallsRequest = File("build/req.json") // default: null
}