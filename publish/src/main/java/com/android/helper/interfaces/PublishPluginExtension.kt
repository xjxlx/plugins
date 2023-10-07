package com.android.helper.interfaces

import org.gradle.api.provider.Property

abstract class PublishPluginExtension {
    /**
     * 组的名字
     */
    abstract val groupId: Property<String>
    /**
     * 插件名称，必传参数
     */
    abstract val artifactId: Property<String>
    /**
     * 版本号，必传参数
     */
    abstract val version: Property<String>

    init {
        groupId.convention("com.github.xjxlx")
        artifactId.convention("dimens")
        version.convention("v2.0.0.4")
    }
}