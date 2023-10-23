// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        // 发布JitPack的依赖版本，【3.0+以上用1.5】，【4.1+以上用2.0】，【4.6+以上用2.1】
        // classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath(libs.com.github.dcendents.android.maven.gradle.plugin)
        // classpath("com.android.tools.build:gradle:7.4.2")
        classpath(libs.com.android.tools.build.gradle)
        // classpath("io.github.xjxlx:common:1.0.0")
         // classpath(libs.io.github.xjxlx.common)
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
    // id("com.android.application") version "7.4.2" apply false
    alias(libs.plugins.com.android.application) apply false
    // id("com.android.library") version "7.4.2" apply false
    alias(libs.plugins.com.android.library) apply false
    // id("org.jetbrains.kotlin.android") version "1.6.21" apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    // id("org.jetbrains.kotlin.jvm") version "1.6.21" apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}
true