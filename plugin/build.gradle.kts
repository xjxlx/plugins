plugins {
    id("java-library")
    id("maven-publish")
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    mavenLocal()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.4.0")
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}