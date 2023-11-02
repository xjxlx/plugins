plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") // 用kotlin语言来开发
    id("com.gradle.plugin-publish") version "1.0.0-rc-1" // 这个是发布到插件门户网站的插件
    `version-catalog` // 1:配置发布插件
}

// false : ALiYun , true: gradle
val switch = false
group = properties["groupId"].toString()
version = properties["catalogVersion"].toString()

if (switch) {
    //<editor-fold desc=" 发布到gradle门户  ">

    // 发布到gradle门户
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
            create("maven") { // 	设置插件的唯一性。id
                id = "${properties["groupId"].toString()}.version" // 短名称显示
                displayName = "versionManager" // 插件的描述
                description = "A plugin used to manage dependencies of various project versions"
                implementationClass = "com.android.catalog.CatalogPlugin"
            }
        }
    }
    //</editor-fold>
} else {
    //<editor-fold desc=" 发布到阿里云  ">
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
                    groupId = properties["groupId"].toString()
                    artifactId = properties["catalogId"].toString()
                    from(components["versionCatalog"])
                    version = properties["catalogVersion"].toString()
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
    implementation("com.android.tools.build:gradle-api:7.4.2")
    implementation(gradleApi()) // gradle sdk
    implementation("org.json:json:20230227") // json 依赖库
    implementation("org.jsoup:jsoup:1.16.1") // html依赖库
}
