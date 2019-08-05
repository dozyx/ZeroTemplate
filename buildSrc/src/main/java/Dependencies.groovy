/*
class BuildPlugins {
    static androidGradle = "com.android.tools.build:gradle:${Version.androidGradleVersion}"
    static kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
}

class Android {
    static minSdkVersion = 19
    static minDebugSdkVersion = 21
    static targetSdkVersion = 27
    static compileSdkVersion = 28
}

class Deps {
    static picasso = "com.squareup.picasso:picasso:${Version.picasso}"
    static threeten = "com.jakewharton.threetenabp:threetenabp:${Version.threeten}"
    static dagger = "com.google.dagger:dagger:${Version.dagger}"
    static daggerAndroid = "com.google.dagger:dagger-android-support:${Version.dagger}"
    static volley = "com.android.volley:volley:${Version.volley}"
    static raintLayout = "com.android.support.raint:raint-layout:${Version.raintLayout}"
    static appcompat = "com.android.support:appcompat-v7:${Version.appcompat}"
    static percentlayout = "androidx.percentlayout:percentlayout:${Version.percentlayout}"
    static universalImageLoader = "com.nostra13.universalimageloader:universal-image-loader:${Version.universalImageLoader}"
    static material = "com.google.android.material:material:${Version.material}"
    static rxjava2 = "io.reactivex.rxjava2:rxjava:${Version.rxjava2}"
    static eventbus = "org.greenrobot:eventbus:${Version.eventbus}"
    static kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"
    static lifecycle = "androidx.lifecycle:lifecycle-extensions:${Version.lifecycle}"
    static annotations = "org.jetbrains:annotations:${Version.annotations}"
    static flexbox = "com.google.android:flexbox:${Version.flexbox}"
    static mpAndroidChart = "com.github.PhilJay:MPAndroidChart:${Version.mpAndroidChart}"
    static barUtil = "com.jaeger.statusbarutil:library:${Version.barUtil}"
    static utilcode = "com.blankj:utilcode:${Version.utilcode}"
    static rxpermissions = "com.github.tbruyelle:rxpermissions:${Version.rxpermissions}"
    static stetho = "com.facebook.stetho:stetho:${Version.stetho}"
    static timber = "com.jakewharton.timber:timber:${Version.timber}"
    static legacySupportV4 = "androidx.legacy:legacy-support-v4:${Version.legacySupportV4}"
    static logger = "com.orhanobut:logger:${Version.logger}"
    static rxAndroid = "io.reactivex.rxjava2:rxandroid:${Version.rxAndroid}"
    static vectordrawable = "androidx.vectordrawable:vectordrawable:${Version.vectordrawable}"
    static recyclerview = "androidx.recyclerview:recyclerview:${Version.recyclerview}"
    static mmkv = "com.tencent:mmkv:${Version.mmkv}"
    static debugDb = "com.amitshekhar.android:debug-db:${Version.debugDb}"
    static background = "com.noober.background:core:${Version.background}"
    static gridlayout = "androidx.gridlayout:gridlayout:${Version.gridlayout}"
    static immersionbar = "com.gyf.immersionbar:immersionbar:${Version.immersionbar}"
    static gson = "com.google.code.gson:gson:${Version.gson}"

    class Hyperion {
        static debugCore = "com.willowtreeapps.hyperion:hyperion-core:${Version.hyperion}"
        static releaseCore = "com.willowtreeapps.hyperion:hyperion-core-no-op:${Version.hyperion}"
        static debugAttr = "com.willowtreeapps.hyperion:hyperion-attr:${Version.hyperion}"
        static debugMeasurement = "com.willowtreeapps.hyperion:hyperion-measurement:${Version.hyperion}"
        static debugDisk = "com.willowtreeapps.hyperion:hyperion-disk:${Version.hyperion}"
        static debugRecorder = "com.willowtreeapps.hyperion:hyperion-recorder:${Version.hyperion}"
        static debugPhoenix = "com.willowtreeapps.hyperion:hyperion-phoenix:${Version.hyperion}"
        static debugCrash = "com.willowtreeapps.hyperion:hyperion-crash:${Version.hyperion}"
        static debugSP = "com.willowtreeapps.hyperion:hyperion-shared-preferences:${Version.hyperion}"
        static debugGeigerCounter = "com.willowtreeapps.hyperion:hyperion-geiger-counter:${Version.hyperion}"
        static debugTimber = "com.willowtreeapps.hyperion:hyperion-timber:${Version.hyperion}"
        static debugbuildConfig = "com.willowtreeapps.hyperion:hyperion-build-config:${Version.hyperion}"
        static debugAppInfo = "com.star_zero:hyperion-appinfo:1.0.0"
    }

    class RxBinding {
        static base = "com.jakewharton.rxbinding3:rxbinding:${Version.rxbinding}"
        static core = "com.jakewharton.rxbinding3:rxbinding-core:${Version.rxbinding}"
        static appcompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:${Version.rxbinding}"
        static drawerlayout = "com.jakewharton.rxbinding3:rxbinding-drawerlayout:${Version.rxbinding}"
        static leanback = "com.jakewharton.rxbinding3:rxbinding-leanback:${Version.rxbinding}"
        static recyclerview = "com.jakewharton.rxbinding3:rxbinding-recyclerview:${Version.rxbinding}"
        static slidingpanelayout = "com.jakewharton.rxbinding3:rxbinding-slidingpanelayout:${Version.rxbinding}"
        static swiperefreshlayout = "com.jakewharton.rxbinding3:rxbinding-swiperefreshlayout:${Version.rxbinding}"
        static viewpager = "com.jakewharton.rxbinding3:rxbinding-viewpager:${Version.rxbinding}"
        static material = "com.jakewharton.rxbinding3:rxbinding-material:${Version.rxbinding}"
    }

    class Room {
        static runtime = "androidx.room:room-runtime:${Version.room}"
        static processor = "androidx.room:room-compiler:${Version.room}"
        static ktx = "androidx.room:room-ktx:${Version.room}"
        static rxjava2 = "androidx.room:room-rxjava2:${Version.room}"
        static guava = "androidx.room:room-guava:${Version.room}"
        static test = "androidx.room:room-testing:${Version.room}"
    }

    class Camera {
        static core = "androidx.camera:camera-core:${Version.camerax}"
        static view = "androidx.camera:camera-view:${Version.camerax}"
        static camera2 = "androidx.camera:camera-camera2:${Version.camerax}"
    }

    class RxLifecycle {
        // If you want to bind to Android-specific lifecycles
        static android = "com.trello.rxlifecycle3:rxlifecycle-android:${Version.rx_lifecycle}"
        // If you want pre-written Activities and Fragments you can subclass as providers
        static components = "com.trello.rxlifecycle3:rxlifecycle-components:${Version.rx_lifecycle}"
        // If you want pre-written support preference Fragments you can subclass as providers
        static preference = "com.trello.rxlifecycle3:rxlifecycle-components-preference:${Version.rx_lifecycle}"
        // If you want to use Android Lifecycle for providers
        static androidLifecycle = "com.trello.rxlifecycle3:rxlifecycle-android-lifecycle:${Version.rx_lifecycle}"
    }

    class ButterKnife {
        static runtime = "com.jakewharton:butterknife:${Version.butterknife}"
        static processor = "com.jakewharton:butterknife-compiler:${Version.butterknife}"
    }

    class Dagger {
        static runtime = "com.google.dagger:dagger:${Version.dagger}"
        static processor = "com.google.dagger:dagger-compiler:${Version.dagger}"
        static androidProcessor = "com.google.dagger:dagger-android-processor:${Version.dagger}"
    }

    class PermissionsDispatcher {
        static runtime = "com.github.hotchemi:permissionsdispatcher:${Version.permissionsdispatcher}"
        static processor = "com.github.hotchemi:permissionsdispatcher-processor:${Version.permissionsdispatcher}"
    }

    class OkHttp {
        static runtime = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
        static logging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
    }

    class Test {
        static junit = "junit:junit:${Version.junit}"
    }

    class Retrofit {
        static runtime = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
        static gson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
        static scalars = "com.squareup.retrofit2:converter-scalars:${Version.retrofit}"
        static rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"
    }

    class LeakCanary {
        static debug = "com.squareup.leakcanary:leakcanary-android:${Version.leakcanary}"
        static release = "com.squareup.leakcanary:leakcanary-android-no-op:${Version.leakcanary}"
        static debugSupportFragment = "com.squareup.leakcanary:leakcanary-support-fragment:${Version.leakcanary}"
    }

    class Navigation {
        static fragment = "android.arch.navigation:navigation-fragment:${Version.navigation}"
        static ui = "android.arch.navigation:navigation-ui:${Version.navigation}"
        static fragmentKtx = "android.arch.navigation:navigation-fragmentt-ktx:${Version.navigation}"
        static uiKtx = "android.arch.navigation:navigation-ui-ktx:${Version.navigation}"
    }
}

class Version {
    static legacySupportV4 = "1.0.0"
    static kotlinVersion = "1.3.31"
    static androidGradleVersion = "3.4.2"
    static picasso = "2.71828"
    static threeten = "1.2.1"
    static leakcanary = "1.6.3"
    static dagger = "2.23.2"
    static retrofit = "2.5.0"
    static junit = "4.12"
    static stickylistheaders = "2.7.0"
    static photoView = "1.2.4"
    static sectorProgressView = "2.0.0"
    static picVkerview = "2.0.8"
    static universalImageLoader = "1.9.5"
    static bugly = "2.6.6.1"
    static bugly_native = "3.3.1"
    static bugly_symtabfile_uploader = "2.1.2"
    static async_http = "1.4.9"
    static ucrop = "1.5.0"
    static bga_refresh_layout = "1.1.8"
    static okhttp = "3.14.0"
    static butterknife = "10.1.0"
    static support = "1.0.0"
    static raintLayout = "2.0.0-alpha2"
    static multidex = "1.0.1"
    static convenient_banner = "2.0.5"
    static loop_view = "0.1.2"
    static eventbus = "3.1.1"
    static fragmentation = "1.3.3"
    static fresco = "1.5.0"
    static weibo = "1.0.0"
    static greendao = "3.2.2"
    static lifecycle = "2.0.0"
    static android_gradle_plugin = "3.4.1"
    static gradle_versions_plugin = "0.20.0"
    static tencent_xinge = "3.2.3-release"
    static tencent_wup = "1.0.0.E-release"// 信鸽依赖
    static tencent_mid = "4.0.6-release" // 信鸽、腾讯统计依赖
    static tencent_mta = "3.3.1-release" // 腾讯统计
    static volley = "1.1.1"
    static rxjava2 = "2.2.7"
    static kotlin = "1.3.31"
    static android_ktx = "1.0.0-beta01" // 与 support-appcompat 冲突
    static permissionsdispatcher = "3.0.1"
    static gson = "2.8.5"
    static test_runner = "1.1.0"
    static espresso = "3.1.1"
    static timber = "4.7.1"
    static logger = "2.2.0"
    static rxpermissions = "0.10.2"
    static utilcode = "1.24.3"
    static flexbox = "1.1.0"
    static mmkv = "1.0.18"
    // 数据库/SharedPreference 调试库，可直接在网页上查看保存的数据。https://github.com/amitshekhariitbhu/Android-Debug-Database
    static debugDb = "1.0.6"
    static mpAndroidChart = "v3.1.0"
    static rx_lifecycle = "3.0.0"
    static rx_android = "2.1.1"
    static barUtil = "1.5.1"
    static rxjava_adapter = "2.5.0"
    static background = "1.4.1"
    static appcompat = "1.1.0-alpha05"
    static androiddevmetrics = "0.6"
    static koin = "1.0.2"
    static material = "1.1.0-alpha02"
    static rxbinding = "3.0.0-alpha2"
    static annotations = "17.0.0"
    static navigation = "1.0.0"
    static immersionbar = "2.3.3"
    static room = "2.1.0-alpha07"
    static stetho = "1.5.1"
    static camerax = "1.0.0-alpha01"
    static percentlayout = "1.1.0"
    static rxAndroid = "2.1.1"
    static vectordrawable = "1.0.1"
    static recyclerview = "1.1.0"
    static gridlayout = "1.0.0"
    static hyperion = "0.9.27"
}*/
