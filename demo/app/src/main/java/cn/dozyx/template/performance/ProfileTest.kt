package cn.dozyx.template.performance

import android.os.Debug
import android.os.Environment
import android.os.Trace
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

class ProfileTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("执行") {
            override fun run() {
                // /storage/emulated/0/Android/data/包名/files/Documents
                Debug.startMethodTracing(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/custom_trace")
                doStupidThing()
                Debug.stopMethodTracing()
            }
        })

        addAction(object : Action("Trace API") {
            override fun run() {
                // 将跟踪事件写入 system trace buffer
                Trace.beginSection("doStupidThing")
                doStupidThing()
                Trace.endSection()
            }
        })
        addAction(object : Action("卡顿") {
            override fun run() {
                doBlock()
            }
        })
    }

    private fun doBlock() {
        Thread.sleep(200)
    }

    private fun doStupidThing() {
        generateData1()
        generateData2()
        generateData1()
    }

    private fun generateData1() {
        val datas = ArrayList<String>(100)
        for (i in 1..100) {
            Thread.sleep(1)
            datas.add("String1 $i")
        }
    }
    private fun generateData2() {
        generateData1()
        val datas = ArrayList<String>(100)
        for (i in 1..100) {
            Thread.sleep(1)
            datas.add("String1 $i")
        }
    }

}