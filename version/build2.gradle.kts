import com.android.common.CommonPlugin

plugins {
    id("java-gradle-plugin")
    id("java-library")
    `version-catalog`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.0.0-rc-1" // 这个是发布到插件门户网站的插件
    id("io.github.xjxlx.common") version "1.0.3"
}

apply<CommonPlugin>()

//apply<VersionCataLogPlugin>()

// 确保您的项目有一组用于您在 Gradle 插件门户存储库中发布的工件（jar 和元数据） 并且也描述了插件作者或插件所属的组织。group
group = Config.plugin_group
// 设置此出版物的版本。如果您之前已经发布了该插件，则需要增加版本。
version = Config.plugin_version_code

//pluginBundle {
//    // 为您的插件项目设置网站。
//    website = "https://github.com/xjxlx/plugins/blob/main/version-catalog/README.md"
//    // 提供源存储库 URI，以便其他人在想要贡献时可以找到它。
//    vcsUrl = "https://github.com/xjxlx/plugins/tree/main/version-catalog"
//    // 设置要用于所有插件的标签，除非在块中被覆盖。plugins,插件的tag。可以通过这个在插件门户上搜索
//    tags = listOf("publish", "android", "plugins")
//}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

java {
    withJavadocJar()
    withSourcesJar()
}

// 发布到gradle门户
//gradlePlugin {
//    // 捆绑包中的每个插件都在块中指定。由于您此时只发布一个插件，因此只会有 一个条目，但如果您的项目将来发布捆绑包，您将在此处列出每个条目。plugins
//    plugins {
//        // 每个插件块的名称不会影响插件配置，但对于提供的每个插件需要是唯一的。
//        create("publish") {
//            // 	设置插件的唯一性。id
//            id = "${group}.version.catalog"
//            // 短名称显示
//            displayName = "VersionCatalog"
//            // 插件的描述
//            description = ""
//            implementationClass = "com.android.helper.plugin.PublishPlugin"
//        }
//    }
//}

dependencies {
//    implementation("com.android.tools.build:gradle-api:7.4.0")
    implementation(gradleApi()) // gradle sdk
    // json 依赖库
    implementation("org.json:json:20230227")
    implementation(project(":pluginUtil"))
}

//catalog {
//    versionCatalog {
//        library("version-catalog", "$${Config.plugin_group}:version:${Config.plugin_version_catalog}")
//    }
//}

afterEvaluate {
    publishing { // 发布配置
        publications {// 发布内容
            create<MavenPublication>("release") {// 注册一个名字为 release 的发布内容
                from(components["java"])
                groupId = "${Config.plugin_group}" // 唯一标识（通常为模块包名，也可以任意）
                artifactId = "version" // 插件名称
                version = "1.0.0"//  版本号
            }
        }
    }
}

//tasks.register("versionCatalog-write", VersionCataLogTask::class.java, object : Action<VersionCataLogTask> {
//    //    dependsOn "sayGood"
//    override fun execute(t: VersionCataLogTask) {
//        t.dependsOn("clean")
//        t.dependsOn("build")
//    }
//})