package cn.dozyx.template

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import cn.dozyx.core.base.BaseApplication
import cn.dozyx.core.debug.ActivityLifecycleLoggerCallbacks
import cn.dozyx.template.pop.HomePopTracker
import com.facebook.stetho.Stetho
import timber.log.Timber

class ZTApplication : BaseApplication() {

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
        Timber.d("ZTApplication.onCreate")
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
