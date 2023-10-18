package com.android.catalog

import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.Config
import utils.FileUtil
import utils.GradleUtil2
import java.io.File

abstract class CataLogPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // 2：创建写入的task
        project.task("tomlTask") { tomlTask ->
            tomlTask.group = Config.Plugin.CATALOG
            project.tasks.forEach { task ->
                val group = task.group
                val name = task.name
                if (group == "build" && name == "build") {
                    tomlTask.finalizedBy(task)
                }
            }

            tomlTask.doLast {
                // 1：从云端写入到本地
                val gradleFile = File(project.rootDir, "${File.separator}gradle${File.separator}${GradleUtil2.libsVersions}")
                println("gradleFile: ${gradleFile.absolutePath}")
                GradleUtil2.writeGradleToLocal(Config.URL.VERSION_PATH, gradleFile)
            }
        }

        //  2: 转换module task
        project.task("convertTask") { convertTask ->
            convertTask.group = Config.Plugin.CATALOG
            project.tasks.forEach { task ->
                val group = task.group
                val name = task.name
                if (group == "build" && name == "build") {
                    convertTask.finalizedBy(task)
                }
            }
            convertTask.doLast {
                // 1: 读取本地的libs文件
                GradleUtil2.initGradle(project)
                // 2: 改变当前build的文件内容
                GradleUtil2.changeGradleFile(FileUtil.getModelGradlePath(project))
            }
        }

        // 3：转换root.gradle task
        project.task("convertRootTask") { convertRootTask ->
            convertRootTask.group = Config.Plugin.CATALOG
            project.tasks.forEach { task ->
                val group = task.group
                val name = task.name
                if (group == "build" && name == "build") {
                    convertRootTask.finalizedBy(task)
                }
            }
            convertRootTask.doLast {
                // 1: 读取本地的libs文件
                GradleUtil2.initGradle(project)
                // 2：改变本地root.gradle
                GradleUtil2.changeRootGradle()
            }
        }
    }
}