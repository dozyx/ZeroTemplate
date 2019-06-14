package com.dozeboy.core.ex

import android.content.Context
import android.content.pm.ApplicationInfo

/**
 * @author dozeboy
 * @date 2019/1/6
 */
fun Context.debuggable() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) > 0