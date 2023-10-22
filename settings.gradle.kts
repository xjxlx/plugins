pluginManagement {
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { setUrl("https://jitpack.io") }
        google()
        mavenCentral()
        mavenLocal()//1、引用插件所在仓库同repositories上传的仓库
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://jitpack.io") }
        google()
        mavenCentral()
        mavenLocal()//1、引用插件所在仓库同repositories上传的仓库
        gradlePluginPortal()
    }

    // 配置使用libs.versions.toml地址
    versionCatalogs {
        create("libs") {
            from(
                files(
                    File(
                        rootDir, "gradle${File.separator}29${File.separator}libs.versions.toml"
                    )
                )
            )
        }
    }
}

rootProject.name = "plugins"
include(":app")
include(":common")
include(":publish")
include(":catalog")
