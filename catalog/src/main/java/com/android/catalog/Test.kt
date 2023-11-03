package com.android.catalog

import utils.HtmlUtil

object Test {

    @JvmStatic
    fun main(args: Array<String>) {
        val htmlForGithub = HtmlUtil.getHtmlForGithub(CatalogPlugin.ORIGIN_VERSION)
        println("htmlForGithub:$htmlForGithub")
    }
}