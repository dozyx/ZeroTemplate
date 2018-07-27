package com.dozeboy.android.core.utli

import android.app.Application

/**
 * @author timon
 * @date 2018/7/23
 */

fun Application.getVersionCode() = packageManager.getPackageInfo(packageName, 0).versionCode

fun Application.getVersionName() = packageManager.getPackageInfo(packageName, 0).versionName

