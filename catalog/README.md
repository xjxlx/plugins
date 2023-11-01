# plugins

## China:

这是一个用来管理各个项目之间版本的插件，使用了这个插件，可以统一的管理各个项目之间的版本依赖，版本信息会在Project.gradle.libs.versions.toml

Usage
--------

```kotlin

Application build . gradle . kts

        @Suppress("DSL_SCOPE_VIOLATION") plugins {
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

## English:

This is a plugin used to manage versions between various projects. With this plugin, version
dependencies between various projects can be managed uniformly. Version information will be found in
Project.grade.libs.versions.toml


Usage
--------

```kotlin
Application build . gradle . kts

        @Suppress("DSL_SCOPE_VIOLATION") plugins {
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
