package com.android.helper.plugin

import com.android.build.api.dsl.LibraryExtension
import com.android.helper.utils.FileUtil
import com.android.helper.utils.TextUtil
import com.android.helper.utils.VersionUtil
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import java.io.File

class PublishPlugin : Plugin<Project> {

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
        project.task("publishTask1") {
            it.group = "build"

            it.doLast {
                println("publishTask ----->doLast")


                // 5：写入github文件
                writeGithub(project)
            }
        }
    }

    /**
     * 写入github的文件，用于把推送的tag转换成github的release的信息
     */
    private fun writeGithub(project: Project) {
        val rootDir = project.rootDir
        val githubFile = File(rootDir, ".github" + File.separator + ".github" + File.separator + "release.yml")
        // 是否需要写入
        val isWrite = if (githubFile.exists()) {
            githubFile.length() <= 0
        } else {
            true
        }
        println("是否需要写入github文件：$isWrite")

        if (isWrite) {
            val projectDir = project.projectDir
            println("projectDir:${projectDir.absolutePath}")

            val githubPath = CommonConstant.githubPath

            val readFile = FileUtil.readFile(File(githubPath))
            println("readFile:$readFile")
            readFile?.forEach {
                println("item:$it")
            }

//            FileUtil.writeFile(githubFile, content)
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