plugins {
    id("java-library")
    id("maven-publish") // 用来发布插件用
    id("org.jetbrains.kotlin.jvm") // 用kotlin语言来开发
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    // gradle sdk
    implementation(gradleApi())
//    implementation("com.android.tools.build:gradle-api:7.4.0")
}

// 发布信息
afterEvaluate {
    publishing { // 发布配置
        publications {// 发布内容
            create<MavenPublication>("release") {// 注册一个名字为 release 的发布内容
                from(components["java"])
                groupId = "com.android.helper" // 唯一标识（通常为模块包名，也可以任意）
                artifactId = "publish" // 插件名称
                version = "1.0.0"//  版本号
            }
        }
    }
}