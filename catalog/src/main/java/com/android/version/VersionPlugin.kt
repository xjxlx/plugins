package com.android.version

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.ivy.util.FileUtil

import java.io.File

abstract class VersionPlugin : Plugin<Project> {

    companion object {
        private const val version = "version"
    }

    override fun apply(project: Project) {
        // 1：创建写入的task
        project.task("tomlTask") { tomlTask ->
            tomlTask.group = version
            tomlTask.doLast {
                // 1：从云端写入到本地
                val gradleFile = File(project.rootDir, "${File.separator}gradle${File.separator}${GradleUtil2.libsVersions}")
                println("gradleFile: ${gradleFile.absolutePath}")
                GradleUtil2.writeGradleToLocal(Config.URL_VERSION_PATH, gradleFile)
            }
        }

        // 2: 转换module task
        project.task("convertTask") { convertTask ->
            convertTask.group = version
            convertTask.doLast {
                // 1: 读取本地的libs文件
                GradleUtil2.initGradle(project)

                // 2: 改变当前build的文件内容
                GradleUtil2.changeGradleFile(FileUtil.getModelGradlePath(project))
            }
        }

        // 3：转换root.gradle task
        project.task("convertRootTask") { convertRootTask ->
            convertRootTask.group = version
            convertRootTask.doLast {
                val dependencies = project.buildscript.dependencies

                project.buildscript.dependencies.components
                project.buildscript.dependencies.components.all {
                    println("all:${it}")
                }


                println("dependencies:${dependencies}")
                val modules = dependencies.modules
                println("modules:${modules}")
                val localGroovy = dependencies.localGroovy()
                println("localGroovy:${localGroovy}")

            }
        }
    }
}