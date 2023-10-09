pluginManagement {

    //  加载本地插件
    resolutionStrategy {
        eachPlugin {
            // useModule("io.github.xjxlx:publish:1.6.2.5")
            println("id:【${requested.id.id}】 namespace:【${requested.id.namespace}】")
            if (requested.id.namespace == "io.github.xjxlx") {
                useModule("io.github.xjxlx:common:1.1.0")
                println("引入了common!")
            }
        }
    }

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
}

rootProject.name = "plugins"
include(":app")
include(":publish")
include(":version")
include(":pluginUtil")
include(":common")
