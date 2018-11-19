package com.zerofate.template.justfortest

import android.app.Application
import android.util.Log
import com.dozeboy.android.core.utli.log.LoggerLogcatTree
import com.dozeboy.android.core.utli.log.RemoteLogTree
import com.orhanobut.logger.*
import com.dozeboy.android.core.utli.log.ZLog
import com.dozeboy.android.core.utli.number.PlusMinusController
import java.math.BigDecimal
import java.math.RoundingMode

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
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e(TAG, "uncaughtException: ", e)
        uncaughtExceptionHandler!!.uncaughtException(t, e)
    }

    companion object {
        private val TAG = "MyApplication"
    }
}
