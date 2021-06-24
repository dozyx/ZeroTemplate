package cn.dozyx.template.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Process
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ProcessUtils
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

        addAction(object : Action("kill process") {
            override fun run() {
                ProcessUtils.killBackgroundProcesses("com.taobao.taobao")
            }
        })

        addAction(object : Action("get all process") {
            override fun run() {
                ProcessUtils.getAllBackgroundProcesses().forEach {
//                    Timber.d("list all process: $it")
                }

                val runningApps = getRunningApps()
                Timber.d("getRunningApps size: ${runningApps.size}")
                runningApps.forEach {
                    Timber.d("list all process: $it")
                }
            }
        })
    }

    fun getRunningProcessByTask(){
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningTasks = activityManager.getRunningTasks(100)
        runningTasks.forEach {

        }
    }

    fun getRunningApps():Set<String> {
        val apps = mutableSetOf<String>()
        val packages = packageManager.getInstalledPackages(0)
        packages.forEach {
            if (ApplicationInfo.FLAG_SYSTEM and it.applicationInfo.flags == 0
                && ApplicationInfo.FLAG_UPDATED_SYSTEM_APP and it.applicationInfo.flags == 0
                && ApplicationInfo.FLAG_STOPPED and it.applicationInfo.flags == 0
            ) {
                Timber.d("ProcessTest.getRunningApps packageName: ${it.packageName}")
                apps.add(it.packageName)
            }
        }
        return apps
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