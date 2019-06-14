package cn.dozyx.core.utli.util

import android.content.Context
import android.content.pm.ApplicationInfo

/**
 * @author dozeboy
 * @date 2019/1/6
 */

class Util {
    companion object {
        var isDebug = false
        fun init(context: Context) {
            isDebug = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        }
    }
}