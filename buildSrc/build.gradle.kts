
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    mavenLocal()//1、引用插件所在仓库同repositories上传的仓库
    gradlePluginPortal()
}