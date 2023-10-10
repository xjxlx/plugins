plugins {
    id("java-gradle-plugin")
    id("java-library")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.0.0-rc-1" // 这个是发布到插件门户网站的插件
}

java {
    withJavadocJar()
    withSourcesJar()
}

group = "io.github.xjxlx"
version = "1.0.0"

pluginBundle {
    website = "https://github.com/xjxlx/plugins/blob/main/versionManager/README.md"
    vcsUrl = "https://github.com/xjxlx/plugins/tree/main/versionManager"
    tags = listOf("common", "android", "version", "versionManager")
}

// 发布到gradle门户
gradlePlugin {
    // 捆绑包中的每个插件都在块中指定。由于您此时只发布一个插件，因此只会有 一个条目，但如果您的项目将来发布捆绑包，您将在此处列出每个条目。plugins
    plugins {
        // 每个插件块的名称不会影响插件配置，但对于提供的每个插件需要是唯一的。
        create("versionManager") {
            // 	设置插件的唯一性。id
            id = "${group}.version"
            // 短名称显示
            displayName = "versionManager"
            // 插件的描述
            description = "A plugin used to manage dependencies of various project versions"
            implementationClass = "com.android.version.VersionPlugin"
        }
    }
}

dependencies {
     implementation(gradleApi()) // gradle sdk
     implementation("org.json:json:20230227")// json 依赖库
     implementation("org.jsoup:jsoup:1.16.1") // html依赖库
}
