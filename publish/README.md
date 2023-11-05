# plugins

## China

### publish:

这是一个用来发布Library的插件，使用这个插件，可以仅仅配置几个属性，就可以在Gradle中省略很多代码

### catalog:
这是一个用来管理各个项目之间版本的插件，使用了这个插件，可以统一的管理各个项目之间的版本依赖，版本信息会在Project.gradle.libs.versions.toml

### 例如：在需要发布的Library中的gradle.build中配置

Usage - Publish
--------

```kotlin
plugins {
    id("io.github.xjxlx.publish") version "xxxx"
}

configure<com.android.helper.plugin.PublishPluginExtension> {
    groupId.set("xxxx") // 默认的数据是：com.github.xjxlx
    artifactId.set("xxxx") // 默认的数据是：model的名字
    version.set("xxxx") // 默认的数据是：获取github上项目中推送的最后的tag
}
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

### publish:
This is a plugin used to publish Library. With this plugin, you can simply configure a few properties and omit a lot of code in Gradle

### catalog:
This is a plugin used to manage versions between various projects. With this plugin, version
dependencies between various projects can be managed uniformly. Version information will be found in
Project.grade.libs.versions.toml

### For example, configure in gradle. build in the Library that needs to be published

Usage - Publish
--------

```kotlin
plugins {
    id("io.github.xjxlx.publish") version "xxxx"
}

configure<com.android.helper.plugin.PublishPluginExtension> {
    groupId.set("xxxx") // 默认的数据是：com.github.xjxlx
    artifactId.set("xxxx") // 默认的数据是：model的名字
    version.set("xxxx") // 默认的数据是：获取github上项目中推送的最后的tag
}

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
