plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") // 用kotlin语言来开发
    id("com.gradle.plugin-publish") version "1.0.0-rc-1" // 这个是发布到插件门户网站的插件
}

pluginBundle {
    // 为您的插件项目设置网站。
    website = "https://github.com/xjxlx/plugins/blob/main/publish/README.md"
    // 提供源存储库 URI，以便其他人在想要贡献时可以找到它。
    vcsUrl = "https://github.com/xjxlx/plugins"
    // 设置要用于所有插件的标签，除非在块中被覆盖。plugins,插件的tag。可以通过这个在插件门户上搜索
    tags = listOf("publish", "android", "plugins")
}

group = "io.github.xjxlx"
version = "1.6.2.5"

// 发布到gradle门户
gradlePlugin {
    // 捆绑包中的每个插件都在块中指定。由于您此时只发布一个插件，因此只会有 一个条目，但如果您的项目将来发布捆绑包，您将在此处列出每个条目。plugins
    plugins {
        // 每个插件块的名称不会影响插件配置，但对于提供的每个插件需要是唯一的。
        create("publish") {
            // 	设置插件的唯一性。id
            id = "${group}.publish"
            // 短名称显示
            displayName = "PublishPlugin"
            // 插件的描述
            description = "A helper plug-in for publishing an application"
            implementationClass = "com.android.helper.plugin.PublishPlugin"
        }
    }
}

// 发布到本地
publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.4.2")
    implementation(gradleApi()) // gradle sdk
}


