object Version {
    const val kotlinVersion = "1.3.31"
    const val androidGradleVersion = "3.4.1"
    const val picasso = "2.71828"
    const val threeten = "1.2.1"
    const val leakcanary = "1.6.3"
    const val dagger = "2.23.2"
    const val retrofit = "2.5.0"
    const val junit = "4.12"
    const val stickylistheaders = "2.7.0"
    const val photoView = "1.2.4"
    const val sectorProgressView = "2.0.0"
    const val picVkerview = "2.0.8"
    const val universalImageLoader = "1.9.5"
    const val bugly = "2.6.6.1"
    const val bugly_native = "3.3.1"
    const val bugly_symtabfile_uploader = "2.1.2"
    const val async_http = "1.4.9"
    const val ucrop = "1.5.0"
    const val bga_refresh_layout = "1.1.8"
    const val okhttp = "3.14.0"
    const val butterknife = "10.1.0"
    const val support = "1.0.0"
    const val constraint_layout = "2.0.0-alpha2"
    const val multidex = "1.0.1"
    const val convenient_banner = "2.0.5"
    const val loop_view = "0.1.2"
    const val eventbus = "3.1.1"
    const val fragmentation = "1.3.3"
    const val fresco = "1.5.0"
    const val weibo = "1.0.0"
    const val greendao = "3.2.2"
    const val lifecycle = "1.1.1"
    const val android_gradle_plugin = "3.4.1"
    const val gradle_versions_plugin = "0.20.0"
    const val tencent_xinge = "3.2.3-release"
    const val tencent_wup = "1.0.0.E-release"// 信鸽依赖
    const val tencent_mid = "4.0.6-release" // 信鸽、腾讯统计依赖
    const val tencent_mta = "3.3.1-release" // 腾讯统计
    const val volley ="1.1.1"
    const val rxjava2 = "2.2.7"
    const val kotlin = "1.3.31"
    const val android_ktx = "1.0.0-beta01" // 与 support-appcompat 冲突
    const val permissionsdispatcher = "3.0.1"
    const val gson = "2.8.5"
    const val test_runner = "1.1.0"
    const val espresso = "3.1.1"
    const val timber = "4.7.1"
    const val logger = "2.2.0"
    const val rxpermissions = "0.10.2"
    const val utilcode = "1.24.3"
    const val flexbox = "1.1.0"
    const val mmkv = "1.0.18"
    // 数据库/SharedPreference 调试库，可直接在网页上查看保存的数据。https://github.com/amitshekhariitbhu/Android-Debug-Database
    const val debug_db = "1.0.6"
    const val mp_android_chart = "v3.1.0"
    const val rx_lifecycle = "3.0.0"
    const val rx_android = "2.1.1"
    const val bar_util = "1.5.1"
    const val rxjava_adapter = "2.5.0"
    const val background = "1.4.1"
    const val appcompat = "1.1.0-alpha05"
    const val androiddevmetrics = "0.6"
    const val koin = "1.0.2"
    const val material = "1.1.0-alpha02"
    const val rxbinding = "3.0.0-alpha2"
    const val annotations = "17.0.0"
    const val navigation = "1.0.0"
    const val immersionbar = "2.3.3"
    const val room = "2.1.0-alpha07"
    const val stetho = "1.5.1"
    const val camerax = "1.0.0-alpha01"
}


object BuildPlugins {
    val androidGradle = "com.android.tools.build:gradle:${Version.androidGradleVersion}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
}

object Android {
    val buildToolsVersion = "27.0.3"
    val minSdkVersion = 19
    val targetSdkVersion = 27
    val compileSdkVersion = 27
    val applicationId = "com.antonioleiva.bandhookkotlin"
    val versionCode = 1
    val versionName = "0.1"

}

object Deps {
    val picasso = "com.squareup.picasso:picasso:${Version.picasso}"
    val threeten = "com.jakewharton.threetenabp:threetenabp:${Version.threeten}"
    val leakcanaryDebug = "com.squareup.leakcanary:leakcanary-android:${Version.leakcanary}"
    val leakcanaryRelease = "com.squareup.leakcanary:leakcanary-android-no-op:${Version.leakcanary}"
    val leakcanaryDebugSupport = "com.squareup.leakcanary:leakcanary-support-fragment:${Version.leakcanary}"
    val dagger = "com.google.dagger:dagger:${Version.dagger}"
    val daggerAndroid = "com.google.dagger:dagger-android-support:${Version.dagger}"
}

object Processor {
    val dagger = "com.google.dagger:dagger-compiler:${Version.dagger}"
    val daggerAndroid = "com.google.dagger:dagger-android-processor:${Version.dagger}"
}

object TestLibs {
    val junit = "junit:junit:4.12"
}