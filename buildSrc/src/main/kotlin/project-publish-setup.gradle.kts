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
    id("io.github.gradle-nexus.publish-plugin")
}

val signingKeyId by extra(null)
val signingPassword by extra(null)
val signingSecretKeyRingFile by extra(null)
val ossrhUsername by extra(null)
val ossrhPassword by extra(null)
val sonatypeStagingProfileId by extra(null)

val secretPropsFile = rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        java.util.Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        extra[name.toString()] = value.toString()
    }
} else {
    extra["ossrh.username"] = System.getenv("OSSRH_USERNAME")
    extra["ossrh.password"] = System.getenv("OSSRH_PASSWORD")
    extra["sonatypeStagingProfileId"] = System.getenv("SONATYPE_STAGING_PROFILE_ID")
    extra["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    extra["signing.password"] = System.getenv("SIGNING_PASSWORD")
    extra["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
}

nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId.set(extra["sonatypeStagingProfileId"].toString())
            username.set(extra["ossrh.username"].toString())
            password.set(extra["ossrh.password"].toString())
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            if (MetaProject.version.endsWith("-SNAPSHOT"))
                snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            else
                snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"))
        }
    }
}
// ./gradlew clean build dokkaHtmlMultiModule
// ./gradlew build test publishAllPublicationsToSonatypeRepository