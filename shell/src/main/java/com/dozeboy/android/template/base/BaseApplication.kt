package com.dozeboy.android.template.base

import android.app.Application
import android.os.StrictMode
import com.blankj.utilcode.util.Utils
import com.dozeboy.android.template.BuildConfig
import com.dozeboy.core.ex.isMainProcess
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber


/**
 * @author dozeboy
 * @date 2018/11/6
 */
abstract class BaseApplication : Application() {

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            configStrictMode()
        }
        super.onCreate()
        if (isMainProcess()) {
            initOnMainProcess()
        }
        Utils.init(this)
        initOnAllProcess()
        initLeakCanary()
        initLog()
    }

    private fun initLog() {
        var tree: Timber.Tree? = null
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter())
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

    abstract fun initOnMainProcess()
    abstract fun initOnAllProcess()

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
