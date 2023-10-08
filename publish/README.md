# plugins

## China:

这是一个用来发布Library的插件，使用这个插件，可以仅仅配置几个属性，就可以在Gradle中省略很多代码

例如：在需要发布的Library的build.gradle.kts中配置
plugins {
    id("io.github.xjxlx.publish") version "xxxx"
}

configure<com.android.helper.plugin.PublishPluginExtension> {
    groupId.set("xxxx") // 默认的数据是：com.github.xjxlx
    artifactId.set("xxxx") // 默认的数据是：model的名字
    version.set("xxxx") // 默认的数据是：获取项目推送的tag
}

## English:

This is a plugin used to publish Library. With this plugin, you can simply configure a few
properties and omit a lot of code in Gradle

For example, configure in the Library that needs to be published

configure<PublishPluginExtension> {
    groupId.set("xx")
    artifactId.set("xx")
    version.set("xx")
}
