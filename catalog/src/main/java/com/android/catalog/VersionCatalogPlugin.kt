package com.android.catalog

import common.Catalog.CATALOG
import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.VersionCataLogUtil

class VersionCatalogPlugin : Plugin<Project> {

    private val mVersionUtil = VersionCataLogUtil()

    override fun apply(project: Project) {
        println("apply versionCatalog --->")

        // 1: create catalog task
        project.tasks.create("catalog") { task ->
            task.group = CATALOG

            // 1.2：找到library的publishing组下的publishToMavenLocal，在执行完publishTask后发布
            project.tasks.find { itemTask ->
                itemTask.group == "build" && itemTask.name == "build"
            }
                ?.let {
                    task.finalizedBy(it)
                }

            // 1.3:配置阿里云信息
            project.buildscript.repositories.maven { maven -> maven.setUrl("https://maven.aliyun.com/repository/public") }

            // 1.4:用户信息-release
            project.buildscript.repositories.maven { maven ->
                maven.credentials { user ->
                    user.username = "6123a7974e5db15d52e7a9d8"
                    user.password = "HsDc[dqcDfda"
                }
                maven.setUrl("https://packages.aliyun.com/maven/repository/2131155-release-wH01IT/")
            }

            // 1.5：用户信息-snapshot
            project.buildscript.repositories.maven { maven ->
                maven.credentials { user ->
                    user.username = "6123a7974e5db15d52e7a9d8"
                    user.password = "HsDc[dqcDfda"
                }
                maven.setUrl("https://packages.aliyun.com/maven/repository/2131155-snapshot-mh62BC/")
            }

            task.doLast {
                // write catalog
                // 1.6 配置settings.gradle
                mVersionUtil.write(project)
            }
        }
    }
}