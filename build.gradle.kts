// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

buildscript {
     val objectboxVersion by extra("4.0.2") // For KTS build scripts

    repositories {
        mavenCentral()
    }

    dependencies {
        // Android Gradle Plugin 4.1.0 or later supported
        classpath(libs.gradle)
        classpath("io.objectbox:objectbox-gradle-plugin:$objectboxVersion")
    }
}

allprojects{
    configurations.all {
        resolutionStrategy {
            cacheChangingModulesFor(0, TimeUnit.SECONDS)
        }
    }
}