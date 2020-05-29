package cn.dozyx.template

import cn.dozyx.core.base.BaseApplication
import cn.dozyx.core.debug.ActivityLifecycleLoggerCallbacks
import com.blankj.utilcode.util.ThreadUtils
import com.facebook.stetho.Stetho
import timber.log.Timber


/**
 * Created by Administrator on 2017/10/23.
 */

class ZTApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleLoggerCallbacks())
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
