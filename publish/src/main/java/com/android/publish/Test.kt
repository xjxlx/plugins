package com.android.publish

import utils.HtmlUtil
import utils.JsonUtil

object Test {

//    @JvmStatic
//    fun main(args: Array<String>) = //        HtmlUtil.getHtmlForGithubJsonArray(CatalogPlugin.ORIGIN_VERSION)
//            ?.let {
//                val jsonArrayToJsonObject = JsonUtil.arrayToObject(it)
//                println("jsonArrayToJsonObject:$jsonArrayToJsonObject")
//                jsonArrayToJsonObject.forEach { obj ->
//                    println("sss:${obj}")
//                }
//            }
//        FileUtil.iteratorsFile("/Users/XJX/.gradle/caches/modules-2", check = { file -> (file.isDirectory) }) { file ->
//            if (file.name.startsWith("io.github.xjxlx")) {
//                println("file--->${file.absolutePath}")
//            }
//        }

    @JvmStatic
    fun main(args: Array<String>) {
        val ORIGIN_VERSION =
            "https://github.com/xjxlx/plugins/blob/master/publish/src/main/java/com/android/publish/version/version.json"
        val htmlForGithubJsonArray = HtmlUtil.getHtmlForGithubJsonArray(ORIGIN_VERSION)
        val arrayToObject = JsonUtil.arrayToObject(htmlForGithubJsonArray)
        println("arrayToObject:$arrayToObject")
    }
}