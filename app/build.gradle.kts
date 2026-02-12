plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")

}

android {
    namespace = "com.qijiavip.drama"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.qijiavip.drama"
        minSdk = 24
        targetSdk = 34  // 暂时使用34，等待广告SDK更新16KB对齐版本
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("../ks.keystore")
            storePassword = "123456"
            keyAlias = "qijia"
            keyPassword = "123456"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("release")
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                    layout.buildDirectory.get().asFile.absolutePath + "/compose_reports",
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                    layout.buildDirectory.get().asFile.absolutePath + "/compose_metrics"
        )
    }
    buildFeatures {
        compose = true
    }
    
    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
        resources {
            pickFirsts += listOf(
                "lib/arm64-v8a/libplt-base.so",
                "lib/armeabi-v7a/libplt-base.so"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.paging.compose)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Coil for image loading
    implementation(libs.coil.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // System UI Controller
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.webview)
//    implementation(libs.accompanist.swiperefresh)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Annotation for @Keep
    implementation(libs.annotation)

    // OAID - 获取设备唯一标识
    implementation("com.gitee.li_yu_jiang:Android_CN_OAID:4.2.16")
    
    // 微信SDK
    implementation(libs.wechat.sdk)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

        implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
    
    // uni-AD广告SDK（排除快手模块避免冲突）
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"), "exclude" to listOf("ks_adsdk-ad.aar", "uniad-ks-release.aar"))))
    implementation("com.github.bumptech.glide:glide:4.12.0")
    
    // 快手内容SDK（用于视频流）
    implementation(files("libs/kssdk-ct-4.12.20.4-publishRelease-5fe79af37b.aar"))

//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
}