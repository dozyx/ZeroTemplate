package cn.dozyx.template.performance

import android.app.Activity
import android.os.Debug
import android.os.Environment
import android.os.Trace
import android.view.Choreographer
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class ProfileTest : BaseTestActivity() {
    companion object {
        val leakActivities = ArrayList<Activity>()
    }

    override fun initActions() {
        addAction(object : Action("执行") {
            override fun run() {
                startTrace()
                doStupidThing()
                stopTrace()
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

        addAction(object : Action("泄漏") {
            override fun run() {
                leakActivities.add(this@ProfileTest)
            }
        })

        addAction(object : Action("监听卡顿") {
            override fun run() {
                Choreographer.getInstance().postFrameCallback(FPSFrameCallback())
            }
        })

    }

    private fun stopTrace() {
        Debug.stopMethodTracing()
    }

    private fun startTrace() {
        // /storage/emulated/0/Android/data/包名/files/Documents
        Debug.startMethodTracing(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/custom_trace")
    }

    private fun doBlock() {
        startTrace()
        appendResult("模拟卡顿")
        Thread.sleep(200)
        stopTrace()
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