# plugins

## China:
这是一个用来发布Library的插件，使用这个插件，可以仅仅配置几个属性，就可以在Gradle中省略很多代码

例如：在需要发布的Library中配置
  configure<PublishPluginExtension> {
      groupId.set("xx")
      artifactId.set("xx")
      version.set("xx")
  }


## English:
This is a plugin used to publish Library. With this plugin, you can simply configure a few properties and omit a lot of code in Gradle

For example, configure in the Library that needs to be published
  configure<PublishPluginExtension> {
      groupId.set("xx")
      artifactId.set("xx")
      version.set("xx")
  }
