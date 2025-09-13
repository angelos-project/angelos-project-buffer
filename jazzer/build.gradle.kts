plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

group = "org.angproj.io.buf"
version = "1.0-SNAPSHOT"

kotlin {
    explicitApi()
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())

    jvm()
    androidTarget {
        publishLibraryVariants("release")
    }

    sourceSets {
        jvmMain.dependencies {
            api(project(":library"))
            implementation("org.angproj.sec:angelos-project-secrand:0.12.1")
            implementation(libs.kotlin.test)
            //implementation(libs.kotlin.jazzer)
            //implementation(libs.kotlin.jazzer.api)
            implementation("com.code-intelligence:jazzer:0.24.0")
            implementation("com.code-intelligence:jazzer-api:0.24.0")
        }
    }
}

android {
    namespace = group.toString()
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}