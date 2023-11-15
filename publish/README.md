# plugins

## China

### publish

这是一个用来发布Library的插件，使用这个插件，可以仅仅配置几个属性，就可以在Gradle中省略很多代码

### catalog

这是一个用来管理各个项目之间版本的插件，使用了这个插件，可以统一的管理各个项目之间的版本依赖，版本信息会在Project.gradle.libs.versions.toml

### 例如：在需要发布的Library中的gradle.build中配置

Usage - Publish
--------

```kotlin
1：第一次：只能这么写
plugins {
    id("io.github.xjxlx.publishing") version "1.0.0"
}

2：配置了catalogs后,可以这么写
@Suppress("DSL_SCOPE_VIOLATION") plugins {
    id("com.android.library")
    id("kotlin-android")
    alias(libs.plugins.io.github.xjxlx.publishing)
}

3：配置发布依赖的信息
configure<com.android.helper.plugin.PublishPluginExtension> {
    groupId.set("xxxx") // 默认的数据是：com.github.xjxlx
    artifactId.set("xxxx") // 默认的数据是：model的名字
    version.set("xxxx") // 默认的数据是：获取github上项目中推送的最后的tag
}

4：配置下载到本地的依赖信息
configure<com.android.helper.plugin.LocalVersionExtension> {
    version.set("xxxx") // 默认的数据是：com.github.xjxlx
}

5：注意事项
4.1：插件的最低支持版本是java - 11
4.2：gradle的版本很重要，如果是8.0 以下 ， 可以使用java -11,如果是8.0 以上 ， 需要使用java -17
4.3:发布插件的jitpack.yml中，如果是gradle8.0 以下 ， 则使用openjdk11,8.0 以上 ， 则使用openjdk17
```

<br>

Usage - Catalog
--------

```kotlin
@Suppress("DSL_SCOPE_VIOLATION") 
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    ...
    id("io.github.xjxlx.catalog") version "1.0.0" apply true
    ...
}
true


Application Tasks Tasks---> catalog 
```

<br>

## English

### publish

This is a plugin used to publish Library. With this plugin, you can simply configure a few properties and omit a lot of code in Gradle

### catalog

This is a plugin used to manage versions between various projects. With this plugin, version
dependencies between various projects can be managed uniformly. Version information will be found in
Project.grade.libs.versions.toml

### For example, configure in gradle. build in the Library that needs to be published

Usage - Publish
--------

```kotlin
1：First time: It can only be written like this
plugins {
    id("io.github.xjxlx.publishing") version "1.0.0"
}

2：After configuring catalogs, it can be written as follows @Suppress(
    "DSL_SCOPE_VIOLATION") plugins {
    id("com.android.library")
    id("kotlin-android")
    alias(libs.plugins.io.github.xjxlx.publishing)
}

3：Configure publishing dependent information configure<com.android.helper.plugin.PublishPluginExtension> {
    groupId.set("xxxx") // default data：com.github.xjxlx
    artifactId.set("xxxx") // default data：model的名字
    version.set("xxxx") // default data：Obtain the last tag pushed in the project on Github
}

4：Configure dependency information downloaded locally
configure<com.android.helper.plugin.LocalVersionExtension> {
    version.set("xxxx") // default data：com.github.xjxlx
}

5：Precautions
4.1：The minimum supported version of the plugin is java-11
4.2：The version of Gradle is very important.If it is below 8.0, you can use Java -11, and if it is above 8.0, you need to use Java - 17
4.3:In jitpack . yml for publishing plugins, if it is below gradle8 .0, openjdk11 will be used; if it is above 8.0, openjdk17 will be used
```

Usage - Catalog
--------

```kotlin
@Suppress("DSL_SCOPE_VIOLATION") 
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    ...
    id("io.github.xjxlx.catalog") version "1.0.0" apply true
    ...
}
true

Application Tasks Tasks---> catalog 
```
