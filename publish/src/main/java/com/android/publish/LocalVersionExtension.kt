package com.android.publish

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal

interface LocalVersionExtension {
    /**
     * 下载当前版本的名字，如果不指定，则为最新的版本
     * 目前的可选项：
     *  1：buddy
     *  2: 31
     */
    @get:Internal
    val version: Property<String>
}