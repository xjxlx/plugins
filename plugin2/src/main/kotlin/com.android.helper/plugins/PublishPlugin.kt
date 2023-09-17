package com.android.helper.plugins

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

/**
 * 发布应用的插件
 */
abstract class PublishPlugin : Plugin<Project> {

    private val VERSION = latestGitTag().ifEmpty { "" }

    override fun apply(project: Project) {
        // 1: 添加插件信息
        project.pluginManager.apply("maven-publish")
        project.group = "com.github.jitpack"
        project.version = "1.0"

        // 2：注册publishing.release
        registerPublishType(project)
    }

    /**
     * 注册一个release的发布类型
     */
    private fun registerPublishType(project: Project) {
        // LibraryExtension.android
//        project.extensions.getByType(LibraryExtension::class.java)
//            .apply {
//                publishing {
//                    singleVariant("release") {
//                        withSourcesJar()
//                        withJavadocJar()
//                        println("> Task :[register:release] success! ")
//                    }
//                }
//
//                // 3: 发布插件
//                publishTask(project, "modelName")
//            }
    }

    /**
     * 获取model的name
     */
    private fun getModelNameForNamespace(nameSpace: String?): String {
        var result = "default"
        nameSpace?.let {
            if (it.contains(".")) {
                result = it.split(".")
                    .reversed()
                    .first()
            }
        }
        return result
    }

    /**
     * 如果要使用：PublishingExtension扩展属性的话，必须要依赖于这个插件
     *   plugins {
     *     id "maven-publish"
     *   }
     */
    private fun publishTask(project: Project, modelName: String) {
        // 在所有的配置都完成之后执行
//        project.afterEvaluate {
//            project.extensions.getByType(PublishingExtension::class.java)
//                .apply {
//                    // 发布内容
//                    this.publications {
//                        create("release", MavenPublication::class.java, object : Action<MavenPublication> {
//                            override fun execute(it: MavenPublication) {
//                                it.groupId = "com.android.apphelper" // 组的名字
//                                it.artifactId = modelName // 插件名称
//                                it.version = VERSION // 版本号
//
//                                // 从当前 module 的 release 包中发布
//                                it.from(components.getByName("release"))
//                                println("> Task :[publish:release] success!")
//                            }
//                        })
//                    }
//                }
//        }
    }

    /**
     * 获取 git 仓库中最新的 tag作为版本号
     */
    private fun latestGitTag(): String {
        val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0").start()
        return process.inputStream.bufferedReader()
            .use { bufferedReader ->
                bufferedReader.readText()
                    .trim()
            }
    }
}