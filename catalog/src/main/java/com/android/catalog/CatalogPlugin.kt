package com.android.catalog

import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.FileUtil
import java.io.File

class CatalogPlugin : Plugin<Project> {

    companion object {
        private const val ORIGIN_GITHUB_CATALOG_PATH = "https://github.com/xjxlx/plugins/blob/master/gradle/29/libs.versions.toml"
        const val ORIGIN_VERSION = "https://github.com/xjxlx/plugins/blob/master/catalog/src/main/java/Version.kt"

        private const val GRADLE_GROUP = "io.github.xjxlx"

        const val CATALOG = "catalog"
        const val ALY_GROUP = "com.android.version"
        const val ALY_VERSION = "1.0.0"

        const val MAVEN_PUBLIC = "https://maven.aliyun.com/repository/public"
        const val MAVEN_RELEASE = "https://packages.aliyun.com/maven/repository/2131155-release-wH01IT/"
        const val MAVEN_SNAPSHOT = "https://packages.aliyun.com/maven/repository/2131155-snapshot-mh62BC/"
        const val ALY_USER_NAME = "6123a7974e5db15d52e7a9d8"
        const val ALY_PASSWORD = "HsDc[dqcDfda"
    }

    private val mVersionUtil = VersionCataLogUtil()
    private val mGradleUtil = GradleUtil()

    override fun apply(project: Project) {
        println("apply versionCatalog --->")

        // 1:配置阿里云信息
        project.parent?.repositories?.maven { maven -> maven.setUrl(MAVEN_PUBLIC) }

        // 2:用户信息-release
        project.parent?.repositories?.maven { maven ->
            maven.credentials { user ->
                user.username = ALY_USER_NAME
                user.password = ALY_PASSWORD
            }
            maven.setUrl(MAVEN_RELEASE)
        }

        // 3：用户信息-snapshot
        project.parent?.repositories?.maven { maven ->
            maven.credentials { user ->
                user.username = ALY_USER_NAME
                user.password = ALY_PASSWORD
            }
            maven.setUrl(MAVEN_SNAPSHOT)
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
        project.tasks.create("localCatalog") { task ->
            task.group = CATALOG
            task.doLast {
                try {
                    val parentFile = File(project.rootDir, "gradle${File.separator}29${File.separator}")
                    parentFile.mkdirs()
                    val gradleFile = File(parentFile, "libs.versions.toml")
                    if (!gradleFile.exists()) {
                        gradleFile.createNewFile()
                    }
                    println("[localCatalog]:[path]:${gradleFile.absolutePath}")
                    mGradleUtil.writeGradleToLocal(ORIGIN_GITHUB_CATALOG_PATH, gradleFile)
                } catch (e: Exception) {
                    println("[localCatalog]:error:${e.message}")
                }
            }
        }

        // 6:删除本地缓存信息
        project.tasks.create("deleteCatalog") { task ->
            task.group = CATALOG
            task.doLast {
                try {
                    // /Users/XJX/.gradle
                    val gradleUserHomeDir = project.gradle.gradleUserHomeDir

                    val gradleCachesFolder = File("${gradleUserHomeDir.absolutePath}/caches/modules-2/files-2.1/${GRADLE_GROUP}")
                    if (gradleCachesFolder.exists()) {
                        FileUtil.deleteFolder(gradleCachesFolder)
                        println("[delete-gradleCaches]:[delete]: completion！")
                    } else {
                        println("[delete-gradleCaches]:gradleCachesFolder not exists!")
                    }

                    // delete .m2
                    gradleUserHomeDir.parent?.let {
                        val m2Folder = File("${it}/.m2/repository")
                        println("m2Folder:${m2Folder.absolutePath}")
                        if (m2Folder.exists()) {
                            FileUtil.deleteFolder(m2Folder)
                            println("[delete-m2]:[delete]: completion！")
                        } else {
                            println("[delete-m2]:m2Folder not exists!")
                        }
                    }
                } catch (e: Exception) {
                    println("[deleteCatalog]:error:${e.message}")
                }
            }
        }
    }
}