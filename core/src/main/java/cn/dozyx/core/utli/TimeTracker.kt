package cn.dozyx.core.utli

import android.os.SystemClock
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TimeTracker private constructor() {

    private val startTime = SystemClock.elapsedRealtimeNanos()

    fun end() {
        Timber.d("TimeTracker.end ${
            TimeUnit.NANOSECONDS.toMillis(
                    SystemClock.elapsedRealtimeNanos() - startTime)
        }")
    }

    fun end(tag: String) {
        Timber.d("TimeTracker.end $tag ${
            TimeUnit.NANOSECONDS.toMillis(
                    SystemClock.elapsedRealtimeNanos() - startTime)
        }")
    }

    companion object {
        fun newTracker() = TimeTracker()
    }

}