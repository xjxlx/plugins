package com.android.version

import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.Config
import utils.FileUtil
import utils.GradleUtil2
import java.io.File
import java.io.InputStream

abstract class VersionPlugin : Plugin<Project> {

    private val mLocalFileInputStream: InputStream? by lazy {
        return@lazy GradleUtil2.getJarInputStream(this@VersionPlugin::class.java)
    }

    override fun apply(project: Project) {
        println("version plugin apply --->")

        // 1：创建写入的task
        project.task("versionGradleTask") { task ->
            task.group = "install"
            task.doLast {
                // 从云端写入到本地
                val gradleFile = File(project.gradle.gradleHomeDir, "libs.versions.toml")
                GradleUtil2.writeGradleToLocal(Config.URL_VERSION_PATH, gradleFile)

                // 2: 直接写入本地
//                mLocalFileInputStream?.let {
//                    FileUtil.writeFile(File(project.rootDir, "gradle" + File.separator + "libs.versions.toml"), it)
//                    println("local version write success !")
//                }
            }
        }

        // 2: 创建转换的task
        project.task("convertGradleTask") { task ->
            task.doLast {
                task.group = "build"
                // 1: 读取本地的libs文件
                GradleUtil2.initGradle(project)

                // 2: 改变当前build的文件内容
//                FileUtil.getModelGradlePath()
                val modelGradlePath = FileUtil.getModelGradlePath(project)
                println("modelGradlePath:$modelGradlePath")
//                changeModuleGradleFile
            }
        }
    }
}