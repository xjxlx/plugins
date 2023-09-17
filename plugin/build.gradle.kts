plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.0.0-rc-1"
}

pluginBundle {
    // 为您的插件项目设置网站。
    website = "http://www.gradle.org/"
    // 提供源存储库 URI，以便其他人在想要贡献时可以找到它。
    vcsUrl = "https://github.com/xjxlx/plugins"
    // 设置要用于所有插件的标签，除非在块中被覆盖。plugins,插件的tag。可以通过这个在插件门户上搜索
    tags = listOf("publish", "android", "发布", "plugins")
}

// https://docs.gradle.org/7.5/userguide/publishing_gradle_plugins.html

// 确保您的项目有一组用于您在 Gradle 插件门户存储库中发布的工件（jar 和元数据） 并且也描述了插件作者或插件所属的组织。group
group = "com.github.xjxlx"
// 设置此出版物的版本。如果您之前已经发布了该插件，则需要增加版本。
version = "1.0"


gradlePlugin {
    // 捆绑包中的每个插件都在块中指定。由于您此时只发布一个插件，因此只会有 一个条目，但如果您的项目将来发布捆绑包，您将在此处列出每个条目。plugins
    plugins {
        // 每个插件块的名称不会影响插件配置，但对于提供的每个插件需要是唯一的。
        create("publish") {
            // 	设置插件的唯一性。id
            id = "com.android.helper.publish"
            // 短名称显示
            displayName = "PublishPlugin"
            // 插件的描述
            description = "A helper plug-in for publishing an application"
            implementationClass = "com.android.helper.plugin.PublishPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.4.0")
    implementation(gradleApi()) // gradle sdk
}

//dependencies {
//    implementation("com.android.tools.build:gradle-api:7.4.0")
//    implementation(gradleApi()) // gradle sdk
//    implementation(localGroovy()) //groovy sdk
//}