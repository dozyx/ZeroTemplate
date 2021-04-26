package cn.dozyx.core.base

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.multidex.MultiDex
import cn.dozyx.core.GlobalConfig
import cn.dozyx.core.ex.debuggable
import cn.dozyx.core.ex.isMainProcess
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * @author dozeboy
 * @date 2019/1/6
 */

abstract class BaseApplication : Application() {

    abstract fun initOnMainProcess()
    abstract fun initOnAllProcess()


    override fun onCreate() {
        if (debuggable()) {
            configStrictMode()
//            registerActivityLifecycleCallbacks(LogActivityLifecycleCallbacks())
        }
        super.onCreate()
        if (isMainProcess()) {
            initOnMainProcess()
        }
        Utils.init(this)
        initOnAllProcess()
        initLeakCanary()
        initLog()
        initCrash()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initCrash() {
        CrashUtils.init {
            Timber.e("BaseApplication.initCrash $it")
        }
    }

    private fun initLog() {
        var tree: Timber.Tree? = null
        if (debuggable()) {
            var builder = PrettyFormatStrategy.newBuilder()
            builder.methodOffset(5).tag(GlobalConfig.TAG)
            builder.showThreadInfo(true)
            Logger.addLogAdapter(AndroidLogAdapter(builder.build()))
            tree = object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, tag, message, t)
                }
            }
        } else {

        }
        tree?.let {
            Timber.plant(tree)
        }
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    private fun configStrictMode() {
        StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .permitDiskReads()
                        .penaltyLog()
                        .build()
        )

        StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .penaltyLog()
                        .penaltyDeath()
                        .build()
        )
    }
}