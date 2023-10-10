plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id("com.android.apphelper.publish")
}
//id 'com.uploadplugin'//3、此处为插件resources下文件名

android {
    namespace = "com.android.plugins"
    compileSdk = 30
//    compileSdk = convertVersion(libs.versions.compileSdks)

    defaultConfig {
        applicationId = "com.android.plugins"
        minSdk = 26
//        targetSdk = convertVersion(libs.versions.targetSdk) //30
        targetSdk = 26
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0") {
        isForce = true
    }
    implementation(libs.core.ktx) {
        isForce = true
    }
}

fun convertVersion(version: Provider<String>): Int {
    return version.get()
        .toInt()
}
