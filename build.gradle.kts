// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    kotlin("plugin.serialization") version "1.8.0"
//    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false
//    alias(libs.plugins.googleKsp) apply true
    // Add KSP plugin alias
//    alias(libs.plugins.googleKsp) apply false
}

/*
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CalendarApp"
include(":app")*/
