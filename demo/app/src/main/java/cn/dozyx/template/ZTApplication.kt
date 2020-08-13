package cn.dozyx.template

import android.content.Context
import cn.dozyx.core.base.BaseApplication
import cn.dozyx.core.debug.ActivityLifecycleLoggerCallbacks
import com.blankj.utilcode.util.ThreadUtils
import com.facebook.stetho.Stetho
import timber.log.Timber
import java.util.*


/**
 * Created by Administrator on 2017/10/23.
 */

class ZTApplication : BaseApplication() {

    override fun attachBaseContext(base: Context?) {
        Timber.d("ZTApplication.attachBaseContext " + Locale.getDefault())
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleLoggerCallbacks())
        if (DebugConfig.DEBUG_LAUNCH) {
            Thread.sleep(200)
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
