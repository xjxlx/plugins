package com.android.publish

import utils.FileUtil

object Test {

    @JvmStatic
    fun main(args: Array<String>) = //        HtmlUtil.getHtmlForGithubJsonArray(CatalogPlugin.ORIGIN_VERSION)
//            ?.let {
//                val jsonArrayToJsonObject = JsonUtil.arrayToObject(it)
//                println("jsonArrayToJsonObject:$jsonArrayToJsonObject")
//                jsonArrayToJsonObject.forEach { obj ->
//                    println("sss:${obj}")
//                }
//            }
        FileUtil.iteratorsFile("/Users/XJX/.gradle/caches/modules-2", check = { file -> (file.isDirectory) }) { file ->
            if (file.name.startsWith("io.github.xjxlx")) {
                println("file--->${file.absolutePath}")
            }
        }
}