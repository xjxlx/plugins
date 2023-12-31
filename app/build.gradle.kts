@Suppress("DSL_SCOPE_VIOLATION") plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") //    id("io.github.xjxlx.common")
}

android {
    namespace = "com.android.plugins"
    compileSdk = libs.versions.compileSdks.get().toInt()

    defaultConfig {
        applicationId = "com.android.plugins"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt() // targetSdk = 26
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0") {
//        isForce = true
    }
    implementation(libs.core.ktx) {
//        isForce = true
    }
}
