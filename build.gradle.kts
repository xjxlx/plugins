// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        // 发布JitPack的依赖版本，【3.0+以上用1.5】，【4.1+以上用2.0】，【4.6+以上用2.1】
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath("com.android.tools.build:gradle:7.4.2")
    }
}

plugins {
    `kotlin-dsl`
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.21" apply false
}