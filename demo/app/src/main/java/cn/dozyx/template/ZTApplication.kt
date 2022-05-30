package cn.dozyx.template

import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import cn.dozyx.core.base.BaseApplication
import cn.dozyx.core.debug.ActivityLifecycleLoggerCallbacks
import cn.dozyx.template.network.okhttp.compat.AndroidPlatform9
import cn.dozyx.template.notification.manager.NotificationManager
import cn.dozyx.template.pop.HomePopTracker
import com.didichuxing.doraemonkit.DoraemonKit
import com.facebook.stetho.Stetho
import okhttp3.internal.platform.Platform

open class ZTApplication : BaseApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.d("Dozyx", "ZTApplication.attachBaseContext")
    }

    override fun getPackageName(): String {
//        return "com.android.chrome"
        return super.getPackageName()
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleLoggerCallbacks())
        registerActivityLifecycleCallbacks(HomePopTracker)
//        SystemBroadcastReceiver.start(this)
        if (DebugConfig.DEBUG_LAUNCH) {
            Thread.sleep(200)
        }
        Looper.getMainLooper().setMessageLogging {
//            Timber.d("handle message: $it")
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            /*Looper.getMainLooper().queue.addIdleHandler {
                Timber.d("test idle")
                return@addIdleHandler true
            }*/
        }
        initDoraemon()
        // attach 调试之后，Debug.isDebuggerConnected() 才会返回 true
//        Timber.d("ZTApplication.onCreate Debug.isDebuggerConnected(): ${Debug.isDebuggerConnected()}")

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            initLowVersionPlatform()
        }
    }

    private fun initLowVersionPlatform() {
        try {
            val jvm = System.getProperty("java.vm.name", "Dalvik")
            System.getProperties()["java.vm.name"] = "jvm"
            println(Platform.get())
            System.getProperties()["java.vm.name"] = jvm
            Platform.resetForTests(AndroidPlatform9.buildIfSupported())
            println(Platform.get())
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun initDoraemon() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        DoraemonKit.install(this)
    }

    override fun initOnMainProcess() {
        Stetho.initializeWithDefaults(this)
        NotificationManager.createChannels(this)
    }

    override fun initOnAllProcess() {

    }

    companion object {
        private val TAG = "ZTApplication"
    }
}
