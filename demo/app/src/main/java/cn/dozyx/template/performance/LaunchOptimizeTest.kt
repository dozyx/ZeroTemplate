package cn.dozyx.template.performance

import android.os.Bundle
import android.os.Trace
import cn.dozyx.template.DebugConfig
import cn.dozyx.template.base.BaseTestActivity

class LaunchOptimizeTest : BaseTestActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Trace.beginSection("Main onCreate")
        super.onCreate(savedInstanceState)
        if (DebugConfig.DEBUG_LAUNCH) {
            for (i in 0..100) {
                Thread(Runnable {
                    Thread.sleep(10)
                }, "custom work thread#$i").start()
            }
            Thread.sleep(500)
        }
        Trace.endSection()
    }

    override fun initActions() {

    }
}