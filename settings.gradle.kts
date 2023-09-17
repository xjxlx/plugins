pluginManagement {
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { setUrl("https://jitpack.io") }
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()//1、引用插件所在仓库同repositories上传的仓库
    }
}
dependencyResolutionManagement {
    //  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "plugins"
include(":app")
//include(":plugin2")
include(":plugin")
