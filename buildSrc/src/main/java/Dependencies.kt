object Versions {
    val kotlinVersion = "1.3.31"
    val androidGradleVersion = "3.4.1"
    val picasso = "2.71828"
    val threeten = "1.2.1"
    val leakcanary = "1.6.3"
    val dagger = "2.23.2"
}


object BuildPlugins {
    val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradleVersion}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
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
    val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    val threeten = "com.jakewharton.threetenabp:threetenabp:${Versions.threeten}"
    val leakcanaryDebug = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
    val leakcanaryRelease = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakcanary}"
    val leakcanaryDebugSupport = "com.squareup.leakcanary:leakcanary-support-fragment:${Versions.leakcanary}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
}

object Processor {
    val dagger = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val daggerAndroid = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
}

object TestLibs {
    val junit = "junit:junit:4.12"
}