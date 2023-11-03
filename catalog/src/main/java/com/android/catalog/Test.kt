package com.android.catalog

import org.json.JSONArray
import org.json.JSONObject
import utils.HtmlUtil

object Test {

    @JvmStatic
    fun main(args: Array<String>) {
        HtmlUtil.getHtmlForGithubJson(CatalogPlugin.ORIGIN_VERSION)
            ?.let {
                println("it:$it")
                JSONArray(it).forEach { array ->
                    if (array is JSONObject) {
                        println("originVersion:$array")
                        if (array.has("originVersion")) {
                            val originVersion = array.getString("originVersion")
                            println("originVersion:$originVersion")
                            return
                        }
                    }
                }
            }
    }
}