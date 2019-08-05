package cn.dozyx.template.justfortest

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import timber.log.Timber
import com.orhanobut.logger.PrettyFormatStrategy
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.LogStrategy
import org.threeten.bp.*


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
        if (BuildConfig.DEBUG) {
        }
        AndroidThreeTen.init(this)
        initLogger()
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, tag, message, t)
            }
        })
        Timber.d(LocalDate.now().toString())
        Timber.d(LocalDateTime.now().toString())
        Timber.d(LocalTime.now().toString())
        Timber.d(MonthDay.now().toString())
        Timber.d(Instant.now().toString())

    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e(TAG, "uncaughtException: ", e)
        uncaughtExceptionHandler!!.uncaughtException(t, e)
    }

    companion object {
        private val TAG = "MyApplication"
    }

    private fun initLogger() {
        val logStrategy = object : LogStrategy {
            private val prefix = arrayOf(". ", " .")
            private var index = 0

            override fun log(priority: Int, @Nullable tag: String?, @NonNull message: String) {
                index = index xor 1 // 异或操作，0 与 1 依次切换
                Log.println(priority, prefix[index] + tag!!, message)
            }
        }
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .logStrategy(logStrategy)
                .showThreadInfo(false) // Optional
                .methodCount(2) // Optional
                .methodOffset(5)
                .tag("JustLog")
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}
