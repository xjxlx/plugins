package com.android.version

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class VersionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("version plugin apply --->")
    }
}