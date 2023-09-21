plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.4.0")
    implementation(gradleApi()) // gradle sdk
    // json 依赖库
    implementation("org.json:json:20230227")
    implementation("org.jsoup:jsoup:1.16.1")

}
