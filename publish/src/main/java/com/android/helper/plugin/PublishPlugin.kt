package com.android.helper.plugin

import com.android.build.api.dsl.LibraryExtension
import com.android.helper.CommonConstant
import com.android.helper.utils.FileUtil
import com.android.helper.utils.HtmlUtil
import com.android.helper.utils.TextUtil
import com.android.helper.utils.VersionUtil
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import java.io.File

class PublishPlugin : Plugin<Project> {

    private val mListGithub: List<String> by lazy {
        val html = arrayListOf<String>()
        val htmlForGithub = HtmlUtil.getHtmlForGithub(CommonConstant.githubPath)
        if (htmlForGithub.isNotEmpty()) {
            html.addAll(htmlForGithub)
        }
        return@lazy html
    }

    private val mListJitpack: List<String> by lazy {
        val jitpack = arrayListOf<String>()
        val list = HtmlUtil.getHtmlForGithub(CommonConstant.jitpack)
        if (list.isNotEmpty()) {
            jitpack.addAll(list)
        }
        return@lazy jitpack
    }

    companion object {
        const val PUBLISH_PLUGIN_ID = "maven-publish"
        const val PUBLISH_TYPE = "release"
    }

    override fun apply(project: Project) {
        // 1：检查是否安装了push插件
        project.pluginManager.findPlugin(PUBLISH_PLUGIN_ID)
            .let {
                if (it == null) {
                    project.pluginManager.apply(PUBLISH_PLUGIN_ID)
                }
            }

        // 2：注册一个片段，用来传输数据使用
        val publishExtension = project.extensions.create("publishExtension", PublishPluginExtension::class.java)

        // 3：注册一个发布的类型
        registerPublishType(project)

        // 4：在项目对象完全配置完成后，去获取自定义的属性
        project.gradle.projectsEvaluated {
            // 获取具体的自定义属性
            val groupId = publishExtension.groupId.convention("com.github.xjxlx")
                .get()
            val artifactId = publishExtension.artifactId.convention(VersionUtil.getModelNameForNamespace(project))
                .get()
            val version = publishExtension.version.convention(VersionUtil.version)
                .get()

            // 注册一个发布的信息
            publishTask(project, groupId, artifactId, version)
        }

        // 4：注册一个发布的task
        project.task("publishTask") {
            it.group = "build"

            it.doLast {
                println("publishTask ----->doLast")

                // 5：写入github文件
                val githubFile = File(File(project.rootDir, ".github" + File.separator + "workflows" + File.separator).apply {
                    if (!exists()) {
                        mkdirs()
                    }
                }, "release.yml").apply {
                    if (!exists()) {
                        createNewFile()
                    }
                }
                writeProject("github", githubFile, mListGithub)

                // 6：写入jitpack文件
                val jitpackFile = File(project.rootDir, "jitpack.yml").apply {
                    if (!exists()) {
                        createNewFile()
                    }
                }
                writeProject("jitpack", jitpackFile, mListJitpack)
            }
        }
    }

    /**
     * 写入文件到项目中
     */
    private fun writeProject(tag: String, outFile: File, list: List<String>) {
        println("$tag -[file-path]: ${outFile.absolutePath}")
        val isWrite = if (outFile.exists()) {
            outFile.length() <= 0
        } else {
            true
        }
        println("$tag - write：$isWrite")
        if (isWrite) {
            FileUtil.writeFile(outFile, list)
        }
    }

    /**
     * 注册一个release的发布类型
     */
    private fun registerPublishType(project: Project) {
        runCatching {
            project.extensions.getByType(LibraryExtension::class.java)
                .publishing {
                    this.singleVariant(PUBLISH_TYPE) {
                        this.withSourcesJar()
                        this.withJavadocJar()
                        println("[register-publishing-$PUBLISH_TYPE]: success !")
                    }
                }
        }.onFailure { throws ->
            println("[register-publishing-$PUBLISH_TYPE]: error:${throws.message} !")
        }
    }

    /**
     * 1：如果要使用：PublishingExtension 扩展属性的话，必须要依赖于这个插件
     *  plugins {
     *      id "maven-publish"
     *  }
     * 2：必须是在项目进行评估的时候去添加
     */
    private fun publishTask(project: Project, groupId: String, artifactId: String, version: String) {
        runCatching {
            println("publishTask - groupId:$groupId artifactId:$artifactId version:$version")
            // 在执行task的时候才会去执行
            project.extensions.getByType(PublishingExtension::class.java)
                .publications {
                    val name = it.findByName(PUBLISH_TYPE)?.name
                    if (TextUtil.isEmpty(name)) {
                        // 注册一个名字为 release 的发布内容
                        it.register(PUBLISH_TYPE, MavenPublication::class.java) { maven ->
                            maven.groupId = groupId
                            maven.artifactId = artifactId
                            maven.version = version

                            // 从当前 module 的 release 包中发布
                            maven.from(project.components.getByName(PUBLISH_TYPE))
                            println("[publishTask]: success !")
                        }
                    } else {
                        println("[publishTask]: type already exists !")
                    }
                }
        }.onFailure { throws ->
            println("[publishTask]:error:${throws.message}")
        }
    }
}