package cn.dozyx.template.performance

import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

class RuntimeActivity : BaseTestActivity() {
    private val runtime = Runtime.getRuntime()
    override fun initActions() {
        addAction(object : Action("info") {
            override fun run() {
                appendResult("runtime.availableProcessors() ${runtime.availableProcessors()}")
                appendResult("runtime.maxMemory() ${byteToMb(runtime.maxMemory())}")
                appendResult("runtime.freeMemory() ${byteToMb(runtime.freeMemory())}")// 不同时间得到的值可能不一样
                appendResult("runtime.totalMemory() ${byteToMb(runtime.totalMemory())}")
            }
        })
    }

    private fun byteToMb(size: Long): Float {
        return size / 1024 / 1024F
    }
}
