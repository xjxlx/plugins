package com.android.helper.plugin

import com.android.build.api.dsl.LibraryExtension
import common.Publish
import common.Publish.PUBLISH_PLUGIN_ID
import common.Publish.PUBLISH_TYPE
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import utils.FileUtil
import utils.TextUtil
import utils.VersionUtil
import java.io.File
import java.io.InputStream

class PublishPlugin : Plugin<Project> {

    private val mJarPath: String? by lazy {
        return@lazy FileUtil.getFilePathForJar(PublishPlugin::class.java)
    }
    private val mGithubStream: InputStream? by lazy {
        mJarPath?.let {
            return@lazy FileUtil.getInputStreamForJar(it, "release.yml")
        }
        return@lazy null
    }
    private val mJitpackStream: InputStream? by lazy {
        mJarPath?.let {
            return@lazy FileUtil.getInputStreamForJar(it, "jitpack.yml")
        }
        return@lazy null
    }

    override fun apply(project: Project) {
        // 1：添加group，不然会找不到id
        project.project.group = Publish.JITPACK
        project.project.version = Publish.JITPACK_VERSION

        // 2：检查是否安装了push插件
        project.pluginManager.findPlugin(PUBLISH_PLUGIN_ID)
            .let {
                if (it == null) {
                    project.pluginManager.apply(PUBLISH_PLUGIN_ID)
                }
            }

        // 3：注册一个发布的类型
        registerPublishType(project)

        // 4：注册一个片段，用来传输数据使用
        val publishExtension = project.extensions.create("publishExtension", PublishPluginExtension::class.java)

        // 5：在项目对象完全配置完成后，去获取自定义的属性
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

        // 6：注册一个发布的task，用来写入一些本地的配置文件
        project.task("publishTask") { task ->
            task.group = Publish.PUBLISH
            // 6.1：先执行清理任务
            // task.dependsOn("clean")

            // 6.2：找到library的publishing组下的publishToMavenLocal，在执行完publishTask后发布
            project.tasks.find { itemTask ->
                itemTask.group == "publishing" && itemTask.name == "publishToMavenLocal"
            }
                ?.let {
                    task.finalizedBy(it)
                }
            // task.finalizedBy("publishToMavenLocal")

            // 6.3：执行写入本地的配置文件
            task.doFirst {
                // println("publishTask ----->doFirst")
                // 6.4：写入github文件
                mGithubStream?.let {
                    val githubFile = File(File(project.rootDir, ".github" + File.separator + "workflows" + File.separator).apply {
                        if (!exists()) {
                            mkdirs()
                        }
                    }, "release.yml").apply {
                        if (!exists()) {
                            createNewFile()
                        }
                    }
                    if (githubFile.length() <= 0) {
                        writeProject("github", githubFile, it)
                    } else {
                        println("[github] file already exists！")
                    }
                }
                // 6.5：写入jitpack文件
                mJitpackStream?.let {
                    val jitpackFile = File(project.rootDir, "jitpack.yml").apply {
                        if (!exists()) {
                            createNewFile()
                        }
                    }
                    if (jitpackFile.length() <= 0) {
                        writeProject("jitpack", jitpackFile, it)
                    } else {
                        println("[jitpack] file already exists！")
                    }
                }
            }

            task.doLast {
                // println("publishTask ----->doLast")
            }
        }
    }

    /**
     * 写入文件到项目中
     */
    private fun writeProject(tag: String, outFile: File, inputStream: InputStream) {
        println("$tag -[file-path]: ${outFile.absolutePath}")
        val isWrite = if (outFile.exists()) {
            outFile.length() <= 0
        } else {
            true
        }
        println("$tag - write：$isWrite")
        if (isWrite) {
            FileUtil.writeFile(outFile, inputStream)
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
                        // this.withSourcesJar()
                        // this.withJavadocJar()
                        println("[publishTask-register-type-$PUBLISH_TYPE]: success !")
                    }
                }
        }.onFailure { throws ->
            println("[publishTask-register-type-$PUBLISH_TYPE]: error:${throws.message} !")
        }
    }

    /**
     * 1：如果要使用：PublishingExtension 扩展属性的话，必须要依赖于这个插件
     *  plugins {
     *      id "maven-publish"
     *  }
     * 2：必须是在项目进行评估的时候去添加
     */
    private fun publishTask(project: Project, groupId: String, artifactId: String, version: String
    ) {
        runCatching {
            // 在执行task的时候才会去执行
            project.extensions.getByType(PublishingExtension::class.java)
                .publications {
                    val name = it.findByName(PUBLISH_TYPE)?.name
                    if (TextUtil.isEmpty(name)) {
                        // 注册一个名字为 release 的发布内容
                        it.register(PUBLISH_TYPE, MavenPublication::class.java) { maven ->
                            println("[publishTask] - [groupId:$groupId] [artifactId:$artifactId] [version:$version]")
                            maven.groupId = groupId
                            maven.artifactId = artifactId
                            maven.version = version

                            // 从当前 module 的 release 包中发布
                            maven.from(project.components.getByName(PUBLISH_TYPE))
                            println("[publishTask-publish]: success !")
                        }
                    } else {
                        println("[publishTask-publish]: type already exists !")
                    }
                }
        }.onFailure { throws ->
            println("[publishTask-publish]:error:${throws.message}")
        }
    }
}