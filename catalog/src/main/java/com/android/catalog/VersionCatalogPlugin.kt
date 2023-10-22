package com.android.catalog

import common.ConfigCatalog.CATALOG
import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.GradleUtil
import utils.VersionCataLogUtil
import java.io.File

class VersionCatalogPlugin : Plugin<Project> {

    private val mVersionUtil = VersionCataLogUtil()
    private val mGradleUtil = GradleUtil()
    private val mVersionPath = "https://github.com/xjxlx/plugins/blob/master/gradle/29/libs.versions.toml"

    override fun apply(project: Project) {
        println("apply versionCatalog --->")

        // 1:配置阿里云信息
        project.buildscript.repositories.maven { maven -> maven.setUrl("https://maven.aliyun.com/repository/public") }

        // 2:用户信息-release
        project.buildscript.repositories.maven { maven ->
            maven.credentials { user ->
                user.username = "6123a7974e5db15d52e7a9d8"
                user.password = "HsDc[dqcDfda"
            }
            maven.setUrl("https://packages.aliyun.com/maven/repository/2131155-release-wH01IT/")
        }

        // 3：用户信息-snapshot
        project.buildscript.repositories.maven { maven ->
            maven.credentials { user ->
                user.username = "6123a7974e5db15d52e7a9d8"
                user.password = "HsDc[dqcDfda"
            }
            maven.setUrl("https://packages.aliyun.com/maven/repository/2131155-snapshot-mh62BC/")
        }

        // 4: create catalog task
        project.tasks.create("catalog") { task ->
            task.group = CATALOG

            // 5：找到library的publishing组下的publishToMavenLocal，在执行完publishTask后发布
//            project.tasks.find { itemTask ->
//                itemTask.group == "build" && itemTask.name == "build"
//            }
//                ?.let {
//                    task.finalizedBy(it)
//                }

            task.doLast {
                // write catalog
                // 6 配置settings.gradle
                mVersionUtil.write(project)
            }
        }

        // 5: 写入到本地
        project.tasks.create("catalogVersion") { task ->
            task.group = CATALOG
            task.doLast {
                val file = File(project.rootDir, "gradle${File.separator}29${File.separator}libs.versions.toml")
                mGradleUtil.writeGradleToLocal(mVersionPath, file)
            }
        }
    }
}