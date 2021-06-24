package cn.dozyx.template

import android.content.Context
import android.os.Build
import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.util.Log
import cn.dozyx.core.base.BaseApplication
import cn.dozyx.core.debug.ActivityLifecycleLoggerCallbacks
import cn.dozyx.template.pop.HomePopTracker
import com.didichuxing.doraemonkit.DoraemonKit
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
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
        initDoraemon()
        initFlipper()
        // attach 调试之后，Debug.isDebuggerConnected() 才会返回 true
//        Timber.d("ZTApplication.onCreate Debug.isDebuggerConnected(): ${Debug.isDebuggerConnected()}")
    }

    private fun initDoraemon() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        DoraemonKit.install(this)
    }

    private fun initFlipper() {
        SoLoader.init(this, false)
        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))// Layout Inspector
            client.addPlugin(SharedPreferencesFlipperPlugin(this))
            client.start()
        }
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
