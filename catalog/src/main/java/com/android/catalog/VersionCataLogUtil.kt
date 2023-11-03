package com.android.catalog

import org.gradle.api.Project
import org.json.JSONObject
import utils.FileUtil
import utils.HtmlUtil
import utils.JsonUtil
import java.io.FileOutputStream

class VersionCataLogUtil {

    private val mListContent = arrayListOf<String>()

    companion object {
        private const val TAG_PLUGIN_MANAGEMENT = "pluginManagement"
        private const val TAG_DEPENDENCY_RESOLUTION_MANAGEMENT = "dependencyResolutionManagement"
        private const val TAG_REPOSITORIES_MODE = "repositoriesMode"
        private const val TAG_REPOSITORIES = "repositories"
        private const val TAG_MAVEN_CATALOG = "versionCatalogs"
        private val jsonList: List<JSONObject>? by lazy {
            HtmlUtil.getHtmlForGithubJsonArray(CatalogPlugin.ORIGIN_VERSION)
                ?.let {
                    val arrayToObject = JsonUtil.arrayToObject(it)
                    return@lazy arrayToObject
                }
            return@lazy null
        }
        private val MAVEN_PUBLIC: String by lazy {
            jsonList?.find { it.has("MAVEN_PUBLIC") }
                ?.getString("MAVEN_PUBLIC")
                ?.let {
                    return@lazy it
                }
            return@lazy ""
        }
        private val MAVEN_RELEASE: String by lazy {
            jsonList?.find { it.has("MAVEN_RELEASE") }
                ?.getString("MAVEN_RELEASE")
                ?.let {
                    return@lazy it
                }
            return@lazy ""
        }
        private val MAVEN_CATALOG: String by lazy {
            jsonList?.find { it.has("MAVEN_CATALOG") }
                ?.getString("MAVEN_CATALOG")
                ?.let {
                    return@lazy it
                }
            return@lazy ""
        }
    }

    fun write(project: Project) {
        val settingsFile = project.rootDir.listFiles()
            ?.find { it.isFile && it.name.contains("settings") }
        settingsFile?.let {
            FileUtil.readFile(settingsFile)
                ?.let { settingsList ->
                    var dependencyResolutionManagementStartFlag = false
                    var repositoriesStartFlag = false

                    var mavenPublicTagFlag = false
                    var mavenPublicReleaseTagFlag = false
                    var catalogFlag = false

                    var count = 0
                    var tempIndex = 0
                    var addCount = 0
                    var addCountFlag = false

                    settingsList.forEachIndexed { index, item ->
                        val trim = item.trim()

                        // 避免错误检测
                        if (trim.startsWith(TAG_PLUGIN_MANAGEMENT)) {
                            dependencyResolutionManagementStartFlag = false
                            repositoriesStartFlag = false
                        }
                        // 检测到了dependencyResolutionManagement标签
                        if (trim.startsWith(TAG_DEPENDENCY_RESOLUTION_MANAGEMENT)) {
                            dependencyResolutionManagementStartFlag = true
                        }
                        // 检测到了repositories标签
                        if (dependencyResolutionManagementStartFlag) {
                            if (trim.startsWith(TAG_REPOSITORIES) && !trim.startsWith(TAG_REPOSITORIES_MODE)) {
                                repositoriesStartFlag = true
                            }
                        }

                        if (repositoriesStartFlag) {
                            if (!addCountFlag) {
                                // 开始计算
                                if (trim.contains("{") && trim.contains("}")) {
                                    count = 0
                                } else if (trim.contains("{")) {
                                    count -= 1
                                } else if (trim.contains("}")) {
                                    count += 1
                                }
                                if (count >= 1) {
                                    tempIndex = index
                                    addCountFlag = true
                                }
                            }

                            // 检测中央公共仓库
                            if (!mavenPublicTagFlag) {
                                mavenPublicTagFlag = (trim.contains(CatalogPlugin.MAVEN_PUBLIC)) && (!trim.startsWith("//"))
                            }
                            // 检测用户信息-release
                            if (!mavenPublicReleaseTagFlag) {
                                mavenPublicReleaseTagFlag = (trim.contains(CatalogPlugin.MAVEN_RELEASE)) && (!trim.startsWith("//"))
                            }

                            // 检测catalog
                            if (!catalogFlag) {
                                catalogFlag = (trim.contains(TAG_MAVEN_CATALOG)) && (!trim.startsWith("//"))
                            }
                        }
                        mListContent.add(item)
                        // println("item:$item count:$count")
                    }

                    // println("[mavenPublic]:$mavenPublicTagFlag")
                    // println("[mavenPublicRelease]:$mavenPublicReleaseTagFlag")
                    // println("[catalog]:$catalogFlag")

                    // 添加阿里云：用户信息 - release
                    if (!mavenPublicReleaseTagFlag) {
                        mListContent.add(tempIndex, MAVEN_RELEASE)
                        addCount += 1
                    }
                    // 添加中央控制仓库
                    if (!mavenPublicTagFlag) {
                        mListContent.add(tempIndex, MAVEN_PUBLIC)
                        addCount += 1
                    }

                    // 3:添加catalog
                    if (!catalogFlag) {
                        mListContent.add(tempIndex + addCount + 1, MAVEN_CATALOG)
                    }

                    // print content
                    mListContent.forEach {
                        println(it)
                    }

                    // write data
                    if (settingsList.size != mListContent.size) {
                        FileOutputStream(settingsFile).use {
                            mListContent.forEach { item ->
                                it.write(item.toByteArray())
                                it.write("\r\n".toByteArray())
                            }
                        }
                    }
                }
        }
    }
}