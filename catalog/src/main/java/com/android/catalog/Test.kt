package com.android.catalog

import utils.HtmlUtil
import utils.JsonUtil

object Test {

    @JvmStatic
    fun main(args: Array<String>) {
        HtmlUtil.getHtmlForGithubJsonArray(CatalogPlugin.ORIGIN_VERSION)
            ?.let {
                val jsonArrayToJsonObject = JsonUtil.arrayToObject(it)
                println("jsonArrayToJsonObject:$jsonArrayToJsonObject")
                jsonArrayToJsonObject.forEach { obj ->
                    println("sss:${obj}")
                }
            }
    }
}