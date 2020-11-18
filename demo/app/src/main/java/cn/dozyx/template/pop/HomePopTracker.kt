package cn.dozyx.template.pop

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.SystemClock
import cn.dozyx.template.activity.SimpleAdActivity

/**
 * 如何排除从二级页面回到首页的情况？
 * @author dozyx
 * @date 11/18/20
 */
object HomePopTracker : Application.ActivityLifecycleCallbacks {
    private const val POP_GAP_TIME = 10 * 1000L
    private var startedCount = 0
    private var lastBackgroundTime = 0L
    private var lastBackgroundElapsedTime = Long.MAX_VALUE
    private val whileListActivities =
        listOf(PopElementTest::class.java, SimpleAdActivity::class.java)
    private var backgroundResumeTopActivityClass: Class<out Activity>? = null// 记录从后台返回时的顶部 Activity

    public fun canPop(): Boolean {
        return lastBackgroundElapsedTime > POP_GAP_TIME && (backgroundResumeTopActivityClass == null || whileListActivities.contains(
            backgroundResumeTopActivityClass
        ))
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (startedCount == 0 && savedInstanceState != null) {
            backgroundResumeTopActivityClass = activity::class.java
        }

    }

    override fun onActivityStarted(activity: Activity) {
        startedCount++
        if (startedCount == 1 && lastBackgroundTime != 0L) {
            // startedCount 为 1 的情况包括冷启动和暖启动(暖启动可能进入的第一个 Activity 不是主 Activity)
            lastBackgroundElapsedTime =
                SystemClock.elapsedRealtime() - lastBackgroundTime // 进入后台经过的时间
            lastBackgroundTime = 0L // 重置
            backgroundResumeTopActivityClass = activity::class.java
        }
        if (startedCount > 1 && !whileListActivities.contains(activity::class.java)) {
            // 清除后台状态
            lastBackgroundElapsedTime = 0
        }
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        startedCount--
        if (startedCount == 0) {
            lastBackgroundTime = SystemClock.elapsedRealtime()
            lastBackgroundElapsedTime = 0
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

}