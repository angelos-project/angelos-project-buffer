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
    id("com.github.nbaztec.coveralls-jacoco") version MetaProject.coverallsVersion
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

coverallsJacoco {
    reportPath = "build/reports/kover/report.xml"

    reportSourceSets = listOf(
        File("buffer/src/commonMain/kotlin/"),
        File("buffer/src/jvmMain/kotlin/"),
        File("buffer/src/jsMain/kotlin/"),
        File("buffer/src/nativeMain/kotlin/"),
    )
}