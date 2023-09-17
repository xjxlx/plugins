package com.android.helper.interfaces

import org.gradle.api.provider.Property

interface PublishExtension {
    /**
     * 组的名字
     */
    val groupId: Property<String>
    /**
     * 插件名称，必传参数
     */
    val artifactId: Property<String>
    /**
     * 版本号，必传参数
     */
    val version: Property<String>
}