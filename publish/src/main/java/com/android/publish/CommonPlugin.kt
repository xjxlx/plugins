package com.android.publish

import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.PrintUtil

class CommonPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        PrintUtil.println("common plugin --->")
    }
}