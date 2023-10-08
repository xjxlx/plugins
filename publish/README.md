# plugins

## China

这是一个用来发布Library的插件，使用这个插件，可以仅仅配置几个属性，就可以在Gradle中省略很多代码

### 例如：在需要发布的Library中的gradle.build中配置

Usage
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
<br>
<br>


## English

This is a plugin used to publish Library. With this plugin, you can simply configure a few properties and omit a lot of code in Gradle

### For example, configure in gradle. build in the Library that needs to be published


Usage
--------

```kotlin
plugins {
    id("io.github.xjxlx.publish") version "xxxx"
}

configure<com.android.helper.plugin.PublishPluginExtension> {
    groupId.set("xxxx") // default data：com.github.xjxlx
    artifactId.set("xxxx") // default data：model.namespace.last name
    version.set("xxxx") // default data：get github origin last tag  
}
```
