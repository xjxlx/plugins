package com.android.publish

import utils.FileUtil
import java.io.File

object Test {

    @JvmStatic
    fun main(args: Array<String>) {
//        HtmlUtil.getHtmlForGithubJsonArray(CatalogPlugin.ORIGIN_VERSION)
//            ?.let {
//                val jsonArrayToJsonObject = JsonUtil.arrayToObject(it)
//                println("jsonArrayToJsonObject:$jsonArrayToJsonObject")
//                jsonArrayToJsonObject.forEach { obj ->
//                    println("sss:${obj}")
//                }
//            }

        val gradleUserHomeDir = "/Users/XJX/.gradle"
        val modules2 = File(gradleUserHomeDir, "caches/modules-2")
        FileUtil.iteratorsFile("/Users/XJX/.gradle/caches/modules-2", check = { file -> (file.isDirectory) }) { file ->
            if (file.name.startsWith("io.github.xjxlx")) {
                println("file--->${file.absolutePath}")
            }
        }
    }
}