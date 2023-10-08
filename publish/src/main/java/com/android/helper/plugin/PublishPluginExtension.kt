package com.android.helper.plugin

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal

interface PublishPluginExtension {

    /**
     * 组的名字
     */
    @get:Internal
    val groupId: Property<String>

    /**
     * 插件名称，必传参数
     */
    @get:Internal
    val artifactId: Property<String>

    /**
     * 版本号，必传参数
     */
    @get:Internal
    val version: Property<String>
}