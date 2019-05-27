object Versions {
    val kotlinVersion = "1.3.31"
    val androidGradleVersion = "3.4.1"
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

object Libs {
}

object TestLibs {
    val junit = "junit:junit:4.12"
}