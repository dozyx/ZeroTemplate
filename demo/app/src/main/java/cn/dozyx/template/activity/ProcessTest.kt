package cn.dozyx.template.activity

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Process
import android.text.TextUtils
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.ProcessUtils
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

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

        addAction(object : Action("cost") {
            override fun run() {
                getCurrentProcessName(this@ProcessTest)
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

    /**
     * 小米 11 上都很快
     */
    fun getCurrentProcessName(context: Context): String? {
        val start1 = System.currentTimeMillis()
        var name = getCurrentProcessNameByFile()
        Timber.d("ProcessTest.getCurrentProcessName file cost: $name ${System.currentTimeMillis() - start1}")
        val start2 = System.currentTimeMillis()
        name = getCurrentProcessNameByAms(context)
        Timber.d("ProcessTest.getCurrentProcessName ams cost: $name ${System.currentTimeMillis() - start2}")
        val start3 = System.currentTimeMillis()
        name = getCurrentProcessNameByReflect(context)
        Timber.d("ProcessTest.getCurrentProcessName reflect cost: $name ${System.currentTimeMillis() - start3}")
        return name
    }

    private fun getCurrentProcessNameByFile(): String? {
        return try {
            val file = File("/proc/" + Process.myPid() + "/" + "cmdline")
            val mBufferedReader = BufferedReader(FileReader(file))
            val processName = mBufferedReader.readLine().trim { it <= ' ' }
            mBufferedReader.close()
            processName
        } catch (e: Exception) {
            ""
        }
    }

    private fun getCurrentProcessNameByAms(context: Context): String? {
        try {
            val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val info = am.runningAppProcesses
            if (info == null || info.size == 0) return ""
            val pid = Process.myPid()
            for (aInfo in info) {
                if (aInfo.pid == pid) {
                    if (aInfo.processName != null) {
                        return aInfo.processName
                    }
                }
            }
        } catch (e: Exception) {
            return ""
        }
        return ""
    }

    private fun getCurrentProcessNameByReflect(context: Context): String? {
        var processName = ""
        try {
            if (context !is Application) {
                return ""
            }
            val app = context
            val loadedApkField = app.javaClass.getField("mLoadedApk")
            loadedApkField.isAccessible = true
            val loadedApk = loadedApkField[app]
            val activityThreadField = loadedApk.javaClass.getDeclaredField("mActivityThread")
            activityThreadField.isAccessible = true
            val activityThread = activityThreadField[loadedApk]
            val getProcessName = activityThread.javaClass.getDeclaredMethod("getProcessName")
            processName = getProcessName.invoke(activityThread) as String
        } catch (e: Exception) {
        }
        return processName
    }
}