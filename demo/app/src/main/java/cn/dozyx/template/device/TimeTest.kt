package cn.dozyx.template.device

import android.os.SystemClock
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class TimeTest :BaseTestActivity() {
    private val startTime = System.currentTimeMillis()
    private val startTimeInNano = System.nanoTime()
    override fun initActions() {
        addAction(object : Action("currentTimeMillis") {
            override fun run() {
                // 随系统时间改变
                Timber.d("TimeTest.run ${System.currentTimeMillis() - startTime}")
            }
        })
        addAction(object : Action("nanoTime") {
            override fun run() {
                // 只能用来计算时间间隔，它的起点是任意一个精确值，可能为负数。只有用于两个值之间的时候，这个方法才有意思。
                // 不随系统时间改变
                Timber.d("TimeTest.run ${System.nanoTime() - startTimeInNano}")
            }
        })
    }
}