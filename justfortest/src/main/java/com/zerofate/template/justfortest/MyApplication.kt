package com.zerofate.template.justfortest

import android.app.Application
import android.util.Log
import com.frogermcs.androiddevmetrics.AndroidDevMetrics

/**
 * @author dozeboy
 * @date 2017/11/13
 */

class MyApplication : Application(), Thread.UncaughtExceptionHandler {
    private var uncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    override fun onCreate() {
        super.onCreate()
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        if (BuildConfig.DEBUG){
            AndroidDevMetrics.initWith(this)
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e(TAG, "uncaughtException: ", e)
        uncaughtExceptionHandler!!.uncaughtException(t, e)
    }

    companion object {
        private val TAG = "MyApplication"
    }
}
