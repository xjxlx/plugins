import common.ConfigCommon

plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") // 用kotlin语言来开发
    id("com.gradle.plugin-publish") version "1.0.0-rc-1" // 这个是发布到插件门户网站的插件
    id("io.github.xjxlx.common")
}

group = ConfigCommon.GROUP
version = ConfigCommon.COMMON_CODE

// 配置插件的发布地址信息
pluginBundle {
    // 为您的插件项目设置网站。
    website = "https://github.com/xjxlx/plugins/blob/main/common/README.md"
    // 提供源存储库 URI，以便其他人在想要贡献时可以找到它。
    vcsUrl = "https://github.com/xjxlx/plugins/tree/main/common"
    // 设置要用于所有插件的标签，除非在块中被覆盖。plugins,插件的tag。可以通过这个在插件门户上搜索
    tags = listOf("common", "android", "utils", "util")
}

// 发布到gradle门户
gradlePlugin {
    // 捆绑包中的每个插件都在块中指定。由于您此时只发布一个插件，因此只会有 一个条目，但如果您的项目将来发布捆绑包，您将在此处列出每个条目。plugins
    plugins {
        // 每个插件块的名称不会影响插件配置，但对于提供的每个插件需要是唯一的。
        create(ConfigCommon.COMMON) {
            // 	设置插件的唯一性。id
            id = "${ConfigCommon.GROUP}.${ConfigCommon.COMMON}"
            // 短名称显示
            displayName = "CommonPlugin"
            // 插件的描述
            description = "A collection of tool classes used to help develop plugins"
            implementationClass = "com.plugin.common.CommonPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.4.2")
    implementation(gradleApi()) // gradle sdk
    implementation("org.json:json:20230227")// json 依赖库
    implementation("org.jsoup:jsoup:1.16.1") // html依赖库
}