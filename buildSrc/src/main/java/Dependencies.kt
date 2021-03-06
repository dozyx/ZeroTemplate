// 使用 default package 时，在 groovy 的 build.gradle 文件无法访问 kotlin 的内部类 https://github.com/gradle/gradle/issues/9251

object BuildPlugins {
    val greendao = "org.greenrobot.greendao"
}

object ClassPath {
    val androidGradle = "com.android.tools.build:gradle:${Version.androidGradleVersion}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
    val greendao = "org.greenrobot:greendao-gradle-plugin:${Version.greendao}"
}

object Android {
    val minSdkVersion = 19
    val minDebugSdkVersion = 21
    val targetSdkVersion = 30
    val compileSdkVersion = 30
}

object Deps {
    val picasso = "com.squareup.picasso:picasso:${Version.picasso}"
    val threeten = "com.jakewharton.threetenabp:threetenabp:${Version.threeten}"
    val dagger = "com.google.dagger:dagger:${Version.dagger}"
    val daggerAndroid = "com.google.dagger:dagger-android-support:${Version.dagger}"
    val volley = "com.android.volley:volley:${Version.volley}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    val percentlayout = "androidx.percentlayout:percentlayout:${Version.percentlayout}"
    val universalImageLoader = "com.nostra13.universalimageloader:universal-image-loader:${Version.universalImageLoader}"
    val material = "com.google.android.material:material:${Version.material}"
    val rxjava1 = "io.reactivex:rxjava:${Version.rxjava1}"
    val rxjava2 = "io.reactivex.rxjava2:rxjava:${Version.rxjava2}"
    val rxjava3 = "io.reactivex.rxjava3:rxjava:${Version.rxjava3}"
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"
    val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Version.lifecycle}"
    val annotations = "org.jetbrains:annotations:${Version.annotations}"
    val flexbox = "com.google.android:flexbox:${Version.flexbox}"
    val mpAndroidChart = "com.github.PhilJay:MPAndroidChart:${Version.mpAndroidChart}"
    val barUtil = "com.jaeger.statusbarutil:library:${Version.barUtil}"
    val utilcode = "com.blankj:utilcode:${Version.utilcode}"
    val rxpermissions = "com.github.tbruyelle:rxpermissions:${Version.rxpermissions}"
    val stetho = "com.facebook.stetho:stetho:${Version.stetho}"
    val timber = "com.jakewharton.timber:timber:${Version.timber}"
    val legacySupportV4 = "androidx.legacy:legacy-support-v4:${Version.legacySupportV4}"
    val logger = "com.orhanobut:logger:${Version.logger}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Version.rxAndroid}"
    val vectordrawable = "androidx.vectordrawable:vectordrawable:${Version.vectordrawable}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Version.recyclerview}"
    val mmkv = "com.tencent:mmkv:${Version.mmkv}"
    val debugDb = "com.amitshekhar.android:debug-db:${Version.debugDb}"
    val background = "com.noober.background:core:${Version.background}"
    val gridlayout = "androidx.gridlayout:gridlayout:${Version.gridlayout}"
    val immersionbar = "com.gyf.immersionbar:immersionbar:${Version.immersionbar}"
    val gson = "com.google.code.gson:gson:${Version.gson}"
    val convenientBanner = "com.bigkoo:convenientbanner:${Version.convenient_banner}"
    val adapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Version.adapter_helper}"
    val calligraphy = "io.github.inflationx:calligraphy3:${Version.calligraphy}"
    val viewpump = "io.github.inflationx:viewpump:${Version.viewpump}"
    val autoService = "com.google.auto.service:auto-service:${Version.auto_service}"
    val javaPoet = "com.squareup:javapoet:${Version.java_poet}"
    val multidex = "androidx.multidex:multidex:${Version.multidex}"
    val circleImageView = "de.hdodenhof:circleimageview:${Version.circle_image}"
    val greendao = "org.greenrobot:greendao:${Version.greendao}"
    val startup = "androidx.startup:startup-runtime:${Version.startup}"
    val tracing = "androidx.tracing:tracing-ktx:${Version.tracing}"
    val exoplayer = "com.google.android.exoplayer:exoplayer:${Version.exoplayer}"
    val core_ktx = "androidx.core:core-ktx:${Version.core_ktx}"
    val lottie = "com.airbnb.android:lottie:${Version.lottie}"
    val process_phoenix = "com.jakewharton:process-phoenix:${Version.process_phoenix}"
    val gp_auth = "com.google.android.gms:play-services-auth:${Version.gp_auth}"
    val tooltip = "com.github.sephiroth74:android-target-tooltip:${Version.tooltip}"
    val materialprogressbar = "me.zhanghai.android.materialprogressbar:library:${Version.materialprogressbar}"
    val notchScreenTool = "com.github.smarxpan:NotchScreenTool:${Version.notchScreenTool}"
}

object AndroidX {
    val activity = "androidx.activity:activity-ktx:${Version.androidx_activity}"
}

object DepsEventBus {
    val core = "org.greenrobot:eventbus:${Version.eventbus}"
    val processor = "org.greenrobot:eventbus-annotation-processor:${Version.eventbus}"
}

object DepsHyperion {
    val debugCore = "com.willowtreeapps.hyperion:hyperion-core:${Version.hyperion}"
    val releaseCore = "com.willowtreeapps.hyperion:hyperion-core-no-op:${Version.hyperion}"
    val debugAttr = "com.willowtreeapps.hyperion:hyperion-attr:${Version.hyperion}"
    val debugMeasurement = "com.willowtreeapps.hyperion:hyperion-measurement:${Version.hyperion}"
    val debugDisk = "com.willowtreeapps.hyperion:hyperion-disk:${Version.hyperion}"
    val debugRecorder = "com.willowtreeapps.hyperion:hyperion-recorder:${Version.hyperion}"
    val debugPhoenix = "com.willowtreeapps.hyperion:hyperion-phoenix:${Version.hyperion}"
    val debugCrash = "com.willowtreeapps.hyperion:hyperion-crash:${Version.hyperion}"
    val debugSP = "com.willowtreeapps.hyperion:hyperion-shared-preferences:${Version.hyperion}"
    val debugGeigerCounter = "com.willowtreeapps.hyperion:hyperion-geiger-counter:${Version.hyperion}"
    val debugTimber = "com.willowtreeapps.hyperion:hyperion-timber:${Version.hyperion}"
    val debugbuildConfig = "com.willowtreeapps.hyperion:hyperion-build-config:${Version.hyperion}"
    val debugAppInfo = "com.star_zero:hyperion-appinfo:1.0.0"
}

object DepsRxBinding {
    val base = "com.jakewharton.rxbinding3:rxbinding:${Version.rxbinding}"
    val core = "com.jakewharton.rxbinding3:rxbinding-core:${Version.rxbinding}"
    val appcompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:${Version.rxbinding}"
    val drawerlayout = "com.jakewharton.rxbinding3:rxbinding-drawerlayout:${Version.rxbinding}"
    val leanback = "com.jakewharton.rxbinding3:rxbinding-leanback:${Version.rxbinding}"
    val recyclerview = "com.jakewharton.rxbinding3:rxbinding-recyclerview:${Version.rxbinding}"
    val slidingpanelayout = "com.jakewharton.rxbinding3:rxbinding-slidingpanelayout:${Version.rxbinding}"
    val swiperefreshlayout = "com.jakewharton.rxbinding3:rxbinding-swiperefreshlayout:${Version.rxbinding}"
    val viewpager = "com.jakewharton.rxbinding3:rxbinding-viewpager:${Version.rxbinding}"
    val material = "com.jakewharton.rxbinding3:rxbinding-material:${Version.rxbinding}"
}

object DepsRoom {
    val runtime = "androidx.room:room-runtime:${Version.room}"
    val processor = "androidx.room:room-compiler:${Version.room}"
    val ktx = "androidx.room:room-ktx:${Version.room}"
    val rxjava2 = "androidx.room:room-rxjava2:${Version.room}"
    val guava = "androidx.room:room-guava:${Version.room}"
    val test = "androidx.room:room-testing:${Version.room}"
}

object DepsCamera {
    // minSdkVersion 至少为 21
    val camera2 = "androidx.camera:camera-camera2:${Version.camerax}"
    val view = "androidx.camera:camera-view:1.0.0-alpha10"
    val lifeCycle = "androidx.camera:camera-lifecycle:${Version.camerax}"
    val extensions = "androidx.camera:camera-extensions:1.0.0-alpha10"
}

object DepsRxLifecycle {
    // If you want to bind to Android-specific lifecycles
    val android = "com.trello.rxlifecycle3:rxlifecycle-android:${Version.rx_lifecycle}"
    // If you want pre-written Activities and Fragments you can subclass as providers
    val components = "com.trello.rxlifecycle3:rxlifecycle-components:${Version.rx_lifecycle}"
    // If you want pre-written support preference Fragments you can subclass as providers
    val preference = "com.trello.rxlifecycle3:rxlifecycle-components-preference:${Version.rx_lifecycle}"
    // If you want to use Android Lifecycle for providers
    val androidLifecycle = "com.trello.rxlifecycle3:rxlifecycle-android-lifecycle:${Version.rx_lifecycle}"
}

object DepsButterKnife {
    val runtime = "com.jakewharton:butterknife:${Version.butterknife}"
    val processor = "com.jakewharton:butterknife-compiler:${Version.butterknife}"
}

object DepsGlide {
    val runtime = "com.github.bumptech.glide:glide:${Version.glide}"
    val processor = "com.github.bumptech.glide:compiler:${Version.glide}"
}

object DepsDagger {
    val runtime = "com.google.dagger:dagger:${Version.dagger}"
    val processor = "com.google.dagger:dagger-compiler:${Version.dagger}"
    val androidProcessor = "com.google.dagger:dagger-android-processor:${Version.dagger}"
}

object DepsPermissionsDispatcher {
    val runtime = "com.github.hotchemi:permissionsdispatcher:${Version.permissionsdispatcher}"
    val processor = "com.github.hotchemi:permissionsdispatcher-processor:${Version.permissionsdispatcher}"
}

object DepsOkHttp {
    val runtime = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
    val logging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
}

object DepsTest {
    val junit = "junit:junit:${Version.junit}"
    val junitExt = "androidx.test.ext:junit:${Version.junit_ext}"
    val testRunner = "androidx.test:runner:${Version.testRunner}"
    val rules = "androidx.test:rules:${Version.testRunner}"
    val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
}

object DepsRetrofit {
    val runtime = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    val gson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    val scalars = "com.squareup.retrofit2:converter-scalars:${Version.retrofit}"
    val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"
    val rxjava3Adapter = "com.squareup.retrofit2:adapter-rxjava3:${Version.retrofit}"
}

object DepsLeakCanary {
    val debug = "com.squareup.leakcanary:leakcanary-android:${Version.leakcanary}"
    val release = "com.squareup.leakcanary:leakcanary-android-no-op:${Version.leakcanary}"
    val debugSupportFragment = "com.squareup.leakcanary:leakcanary-support-fragment:${Version.leakcanary}"
}

object DepsNavigation {
    val fragment = "androidx.navigation:navigation-fragment:${Version.navigation}"
    val ui = "androidx.navigation:navigation-ui:${Version.navigation}"
    val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
    val uiKtx = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
}

object DepsPaging {
    val runtime = "androidx.paging:paging-runtime:${Version.paging}"
    val rxjava2 = "androidx.paging:paging-rxjava2:${Version.paging}"
    // common 用于 testImplementation
    val common = "androidx.paging:paging-common:${Version.paging}"

    val runtimeKtx = "androidx.paging:paging-runtime-ktx:${Version.paging}"
    val rxjava2Ktx = "androidx.paging:paging-rxjava2-ktx:${Version.paging}"
    val commonKtx = "androidx.paging:paging-common-ktx:${Version.paging}"
}

object DepsExoPlayer {
    val okhttp = "com.google.android.exoplayer:extension-okhttp:${Version.exoplayer}"
}

object DepsDoraemonKit {
    val dokitx = "com.didichuxing.doraemonkit:dokitx:${Version.doraemonkit}"
    val dokitx_no_op = "com.didichuxing.doraemonkit:dokitx-no-op:${Version.doraemonkit}"
}

object DepsFlipper {
    val flipper = "com.facebook.flipper:flipper:${Version.flipper}"
    val flipper_noop = "com.facebook.flipper:flipper-noop:${Version.flipper}"
    val soloader = "com.facebook.soloader:soloader:${Version.soloader}"
}

object Version {
    const val legacySupportV4 = "1.0.0"
    const val kotlinVersion = "1.3.31"
    const val androidGradleVersion = "3.6.1"
    const val picasso = "2.71828"
    const val threeten = "1.2.1"
    const val leakcanary = "1.6.3"
    const val dagger = "2.28"
    const val retrofit = "2.9.0"
    const val junit = "4.12"
    const val junit_ext = "1.1.1"
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
    const val okhttp = "3.14.4"
    const val butterknife = "10.1.0"
    const val support = "1.0.0"
    const val constraintLayout = "2.0.4"
    const val multidex = "2.0.1"
    const val convenient_banner = "2.1.5"
    const val loop_view = "0.1.2"
    const val eventbus = "3.1.1"
    const val fragmentation = "1.3.3"
    const val fresco = "1.5.0"
    const val weibo = "1.0.0"
    const val greendao = "3.3.0"
    const val lifecycle = "2.0.0"
    const val android_gradle_plugin = "3.4.1"
    const val gradle_versions_plugin = "0.20.0"
    const val tencent_xinge = "3.2.3-release"
    const val tencent_wup = "1.0.0.E-release"// 信鸽依赖
    const val tencent_mid = "4.0.6-release" // 信鸽、腾讯统计依赖
    const val tencent_mta = "3.3.1-release" // 腾讯统计
    const val volley = "1.1.1"
    const val rxjava1 = "1.2.1"
    const val rxjava2 = "2.2.16"
    const val rxjava3 = "3.0.12"
    const val kotlin = "1.3.31"
    const val core_ktx = "1.3.2" // 与 support-appcompat 冲突
    const val permissionsdispatcher = "3.0.1"
    const val gson = "2.8.5"
    const val testRunner = "1.1.0"
    const val espresso = "3.1.1"
    const val timber = "4.7.1"
    const val logger = "2.2.0"
    const val rxpermissions = "0.10.2"
    const val utilcode = "1.30.5"
    const val flexbox = "1.1.0"
    const val mmkv = "1.0.18"
    // 数据库/SharedPreference 调试库，可直接在网页上查看保存的数据。https://github.com/amitshekhariitbhu/Android-Debug-Database
    const val debugDb = "1.0.6"
    const val mpAndroidChart = "v3.1.0"
    const val rx_lifecycle = "3.0.0"
    const val rx_android = "2.1.1"
    const val barUtil = "1.5.1"
    const val rxjava_adapter = "2.5.0"
    const val background = "1.5.6"
    const val appcompat = "1.3.0"
    const val androiddevmetrics = "0.6"
    const val koin = "1.0.2"
    const val material = "1.3.0"
    const val rxbinding = "3.1.0"
    const val annotations = "17.0.0"
    const val navigation = "2.1.0"
    const val paging = "2.1.0"
    const val immersionbar = "2.3.3"
    const val room = "2.2.5"
    const val stetho = "1.5.1"
    const val camerax = "1.0.0-beta03"
    const val percentlayout = "1.0.0"
    const val rxAndroid = "2.1.1"
    const val vectordrawable = "1.0.1"
    const val recyclerview = "1.2.1"
    const val gridlayout = "1.0.0"
    const val hyperion = "0.9.27"
    const val glide = "4.10.0"
    const val adapter_helper = "3.0.6"
    const val calligraphy = "3.1.1"
    const val viewpump = "2.0.3"
    const val auto_service = "1.0-rc6"
    const val java_poet = "1.12.1"
    const val circle_image = "3.1.0"
    const val startup = "1.0.0-alpha02"
    const val tracing = "1.0.0-beta01"
    const val exoplayer = "2.12.1"
    const val lottie = "3.5.0"
    const val process_phoenix = "2.0.0"
    const val gp_auth = "19.0.0"
    const val tooltip = "2.0.4"
    const val materialprogressbar = "1.6.1"
    const val notchScreenTool = "0.0.1"
    const val doraemonkit = "3.3.5"
    const val flipper = "0.85.0"
    const val soloader = "0.10.1"
    const val androidx_activity = "1.2.3"
}