package cn.dozyx.template

import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import cn.dozyx.core.base.BaseApplication
import cn.dozyx.core.debug.ActivityLifecycleLoggerCallbacks
import cn.dozyx.template.pop.HomePopTracker
import com.didichuxing.doraemonkit.DoraemonKit
import com.facebook.stetho.Stetho

open class ZTApplication : BaseApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.d("Dozyx", "ZTApplication.attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleLoggerCallbacks())
        registerActivityLifecycleCallbacks(HomePopTracker)
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
    }

    private fun initDoraemon() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        DoraemonKit.install(this)
    }

    override fun initOnMainProcess() {
        Stetho.initializeWithDefaults(this)
    }

    override fun initOnAllProcess() {

    }

    companion object {
        private val TAG = "ZTApplication"
    }
}
