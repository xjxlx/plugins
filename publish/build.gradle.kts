plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") // 用kotlin语言来开发
    id("com.gradle.plugin-publish") version "1.0.0-rc-1" // 这个是发布到插件门户网站的插件
    `version-catalog` // 1:配置发布插件
    id("maven-publish")
}

// false : ALiYun , true: gradle
val switch = false
if (switch) {
    //<editor-fold desc=" 发布到gradle门户  ">
    System.out.println("gradle--->")
    val gradleGroupId = "io.github.xjxlx"
    val gradleVersion = "1.0.0"

    group = gradleGroupId
    version = gradleVersion

    // 发布的插件信息
    pluginBundle {
        // 为您的插件项目设置网站。
        website = "https://github.com/xjxlx/plugins/blob/main/publish/README.md"
        // 提供源存储库 URI，以便其他人在想要贡献时可以找到它。
        vcsUrl = "https://github.com/xjxlx/plugins"
        // 设置要用于所有插件的标签，除非在块中被覆盖。plugins,插件的tag。可以通过这个在插件门户上搜索
        tags = listOf("publish", "android", "plugins", "catalog", "version")
    }

    // 发布到gradle门户
    gradlePlugin {
        // 捆绑包中的每个插件都在块中指定。由于您此时只发布一个插件，因此只会有 一个条目，但如果您的项目将来发布捆绑包，您将在此处列出每个条目。plugins
        plugins {
            // 每个插件块的名称不会影响插件配置，但对于提供的每个插件需要是唯一的。
            create("maven") {
                // 	设置插件的唯一性。id
                id = "${gradleGroupId}.publish"
                // 短名称显示
                displayName = "PublishPlugin"
                // 插件的描述
                description = "A helper plug-in for publishing an application"
                implementationClass = "com.android.publish.PublishPlugin"
            }
        }
    }
    //</editor-fold>
} else {
    //<editor-fold desc=" 发布到阿里云  ">
    System.out.println("aly--->")

    val alyGroupId = "com.android"
    val alyVersion = "1.0.0"

    group = alyGroupId
    version = alyVersion

    // 2：配置发布的跟文件，这里可以配置.toml文件，也可以配置具体的信息，可以具体查看官网
    catalog {
        versionCatalog {
            from(files(File(rootDir, "gradle${File.separator}29${File.separator}libs.versions.toml")))
        }
    }

    // 3：配置具体的发布信息以及远程地址
    afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    groupId = alyGroupId
                    artifactId = "catalogs"
                    version = alyVersion
                    from(components["versionCatalog"])
                }
            }

            repositories {
                maven {
                    setUrl("https://packages.aliyun.com/maven/repository/2131155-release-wH01IT/")
                    credentials {
                        username = "6123a7974e5db15d52e7a9d8"
                        password = "HsDc[dqcDfda"
                    }
                }
            }
        }
    }
    //</editor-fold>
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(gradleApi()) // gradle sdk
    implementation(libs.gradle.api)
    implementation(libs.org.json)// json 依赖库
    implementation(libs.org.jsoup)// html依赖库
}
