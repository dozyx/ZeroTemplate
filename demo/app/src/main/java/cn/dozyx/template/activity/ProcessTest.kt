package cn.dozyx.template.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Process
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class ProcessTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("other process") {
            override fun run() {
                startActivity(Intent(this@ProcessTest, OtherProcessActivity::class.java))
            }
        })

        addAction(object : Action("get process name") {
            override fun run() {
                val start = System.currentTimeMillis()
                val processName = getProcessName(this@ProcessTest)
                Timber.d("ProcessTest.run $processName ${System.currentTimeMillis() - start}")
            }
        })
    }
    fun getProcessName(context: Context): String? {
        val myPid = Process.myPid()
        val manager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val processes = manager.runningAppProcesses
        if (processes != null) {
            for (process in processes) {
                if (process.pid == myPid) {
                    return process.processName
                }
            }
        }
        return ""
    }
}