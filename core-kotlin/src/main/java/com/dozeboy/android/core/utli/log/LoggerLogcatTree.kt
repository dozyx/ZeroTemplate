package com.dozeboy.android.core.utli.log

import android.util.Log
import com.orhanobut.logger.*
import timber.log.Timber

/**
 * @author dozeboy
 * @date 2018/7/26
 */
class LoggerLogcatTree : Timber.Tree() {
    init {
        val logStrategy = object : LogStrategy {
            val prefix = arrayOf(".", ",")
            var index = 0
            override fun log(priority: Int, tag: String?, message: String) {
                index = index xor 1
                Log.println(priority, prefix[index] + tag, message)
            }
        }
        val formatStrategy =
            PrettyFormatStrategy.newBuilder().logStrategy(logStrategy).showThreadInfo(false)
                .methodCount(2)
                .methodOffset(6).tag(LogUtil.TAG).build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Logger.log(priority, tag, message, t)
    }
}