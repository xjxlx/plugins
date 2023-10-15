package com.android.version

import utils.GradleUtil
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

class VersionTask : DefaultTask() {

    private val mGradleUtil: GradleUtil = GradleUtil()

    @TaskAction
    fun taskAction() {
        println("version manager taskAction: ------> ")
        val project = project
        mGradleUtil.initGradle(project)
        mGradleUtil.writeGradleToLocal(URL_PATH, File(project.rootDir, "gradle" + File.separator + "libs2.versions.toml"))
        mGradleUtil.changeModules()
        mGradleUtil.changeRootGradle()
        println("versionTask success!")
    }

    companion object {
        private const val URL_PATH =
            "https://github.com/xjxlx/plugins/blob/39a705f313bec743e2c0437ce0f61a64a63c60f2/gradle/libs.versions.toml"
    }
}