package com.dozeboy.core.ex

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo

/**
 * @author dozeboy
 * @date 2019/1/6
 */
fun Context.debuggable() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) > 0

fun Context.isMainProcess(): Boolean {
    var processName = ""
    val pid = android.os.Process.myPid()
    val activityManager = getSystemService(
            Context.ACTIVITY_SERVICE
    ) as ActivityManager
    val allProcesses = activityManager.runningAppProcesses
    for (process in allProcesses) {
        if (process.pid == pid) {
            processName = process.processName
            break
        }
    }
    return packageName == processName
}