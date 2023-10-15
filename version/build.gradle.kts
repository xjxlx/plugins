plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") // 用kotlin语言来开发
    id("com.gradle.plugin-publish") version "1.0.0-rc-1" // 这个是发布到插件门户网站的插件
    `version-catalog` // 1：version control
}

// 2：指定目录的位置
catalog {
    versionCatalog {
        from(files("${rootDir.absoluteFile}/gradle/libs.versions.toml"))
    }
}

// 3：配置发布catalog到云端的信息
publishing {
    publications {
        create<MavenPublication>("maven") {
            group = Config.plugin_group
            version = Config.plugin_version
            artifactId = "catalog"
            from(components["versionCatalog"])
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

group = Config.plugin_group
version = Config.plugin_version

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
            id = "$group.version"
            // 短名称显示
            displayName = "versionManager"
            // 插件的描述
            description = "A plugin used to manage dependencies of various project versions"
            implementationClass = "com.android.version.VersionPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.4.2")
    implementation(gradleApi()) // gradle sdk
    implementation("org.json:json:20230227")// json 依赖库
    implementation("org.jsoup:jsoup:1.16.1") // html依赖库
}
