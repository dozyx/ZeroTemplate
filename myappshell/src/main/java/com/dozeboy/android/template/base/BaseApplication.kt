package com.dozeboy.android.template.base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.StrictMode

import com.blankj.utilcode.util.Utils
import com.dozeboy.android.template.BuildConfig
import com.squareup.leakcanary.LeakCanary


/**
 * @author dozeboy
 * @date 2018/11/6
 */
abstract class BaseApplication : Application() {

    private val isMainProcess: Boolean
        get() {
            var processName = ""
            val pid = android.os.Process.myPid()
            val activityManager = getSystemService(
                    Context.ACTIVITY_SERVICE
            ) as ActivityManager
            val allProcesses = activityManager.runningAppProcesses
            for (process in allProcesses) {
                if (process.pid == pid) {
                    processName = process.processName
                    break
                }
            }
            return packageName == processName
        }

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            configStrictMode()
        }
        super.onCreate()
        if (isMainProcess) {
            initOnMainProcess()
        }
        initOnAllProcess()
        initLeakCanary()
        Utils.init(this)
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
