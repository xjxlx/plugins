plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.0.0-rc-1"
}

pluginBundle {
    // 为您的插件项目设置网站。
    website = "https://github.com/"
    // 提供源存储库 URI，以便其他人在想要贡献时可以找到它。
    vcsUrl = "https://github.com/xjxlx/plugins"
    // 设置要用于所有插件的标签，除非在块中被覆盖。plugins,插件的tag。可以通过这个在插件门户上搜索
    tags = listOf("publish", "android", "发布", "plugins")
}

// https://docs.gradle.org/7.5/userguide/publishing_gradle_plugins.html

// 确保您的项目有一组用于您在 Gradle 插件门户存储库中发布的工件（jar 和元数据） 并且也描述了插件作者或插件所属的组织。group
group = "com.android.helper"
// 设置此出版物的版本。如果您之前已经发布了该插件，则需要增加版本。
version = "1.0.0"

java {
    withJavadocJar()
    withSourcesJar()
}

// 发布到gradle门户
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

// 发布到本地
afterEvaluate {
    publishing { // 发布配置
        publications {// 发布内容
            create<MavenPublication>("release") {// 注册一个名字为 release 的发布内容
                from(components["java"])
                groupId = "com.android.helper" // 唯一标识（通常为模块包名，也可以任意）
                artifactId = "publish" // 插件名称
                version = "1.0.0"//  版本号
            }
        }
    }
}

//dependencies {
//    implementation("com.android.tools.build:gradle-api:7.4.0")
//    implementation(gradleApi()) // gradle sdk
//    implementation(localGroovy()) //groovy sdk
//}