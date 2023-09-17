plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.android.apphelper.publish")
}
//id 'com.uploadplugin'//3、此处为插件resources下文件名

android {
    namespace = "com.android.plugins"
    compileSdk = 31

    defaultConfig {
        applicationId = "com.android.plugins"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    implementation("androidx.core:core-ktx:1.6.0")
}