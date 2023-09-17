// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("com.android.helper.plugins:publish2:1.0.0")
        //2、具体插件名称，拼接原则：pom.groupId:pom.artifactId:pom.version
//        classpath 'com.geexy:uploadPlugin:1.0.7'
    }
}

plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
}
