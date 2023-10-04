package com.android.helper.plugin

import VersionUtil
import com.android.build.api.dsl.LibraryExtension
import com.android.helper.interfaces.PublishPluginExtension
import com.android.helper.utils.TextUtil
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

class PublishPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // 1：注册一个片段，用来传输数据使用
        val publishExtension = project.extensions.create("publishExtension", PublishPluginExtension::class.java)

        // 2：检查是否安装了push插件
        val plugin = project.pluginManager.findPlugin("maven-publish")
        if (plugin == null) {
            // 安装插件
            project.pluginManager.apply("maven-publish")

            // String id = plugin.getId();
            // String name = plugin.getName();
            // boolean hasPlugin = project.getPluginManager().hasPlugin(id);
            // println("hasPlugin:" + hasPlugin);
            // println("id:" + id);
            // println("name:" + name);
            // PluginContainer plugins = project.getPlugins();
        }

        // 3：注册一个发布的task
        project.task("publishTask") {
            // 注册一个发布的类型
            registerPublishType(project)

            // 发布插件
            publishTask(project, publishExtension.groupId.get(), publishExtension.artifactId.get(), publishExtension.version.get())
        }
    }

    /**
     * 注册一个release的发布类型
     */
    private fun registerPublishType(project: Project) {
        val libraryExtension = project.extensions.getByType(LibraryExtension::class.java)
        libraryExtension.publishing {
            this.singleVariant("release") {
                this.withSourcesJar()
                this.withJavadocJar()
            }
        }
    }

    /**
     * 如果要使用：PublishingExtension 扩展属性的话，必须要依赖于这个插件
     *
     * plugins {
     *      id "maven-publish"
     * }
     */
    private fun publishTask(project: Project, groupId: String, artifactId: String, version: String) {
        // 获取插件版本信息
        println("groupId:$groupId artifactId:$artifactId version:$version")

        // 在所有的配置都完成之后执行
        project.afterEvaluate {
            it.extensions.getByType(PublishingExtension::class.java)
                .publications { publications ->
                    publications.create("release", MavenPublication::class.java, object : Action<MavenPublication> {
                        override fun execute(t: MavenPublication) {
                            t.groupId = groupId
                            t.artifactId = artifactId

                            var gitVersion = VersionUtil.VERSION
                            if (TextUtil.isEmpty(gitVersion)) {
                                gitVersion = version
                            }
                            t.version = gitVersion

                            // 发布
                            t.from(it.components.getByName("release"))
                        }
                    })
                }
        }


        project.afterEvaluate { project1: Project ->
            val publish = project1.extensions.getByType(PublishingExtension::class.java)
            publish.publications.create("release", MavenPublication::class.java, Action { maven: MavenPublication ->
                maven.groupId = groupId
                maven.artifactId = artifactId
                var gitVersion = VersionUtil.VERSION
                if (TextUtil.isEmpty(gitVersion)) {
                    gitVersion = version
                }
                maven.version = gitVersion

                // 发布
                maven.from(project1.components.getByName("release"))
            })
        }
    }
}