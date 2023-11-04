// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
//    `kotlin-dsl`
    // id("com.android.application") version "7.4.2" apply false
    // id("com.android.library") version "7.4.2" apply false
    // id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    // id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false

    // id("io.github.xjxlx.publish") version "1.0.0" apply false
    // id("io.github.xjxlx.catalog") version "1.0.0" apply false
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}
true