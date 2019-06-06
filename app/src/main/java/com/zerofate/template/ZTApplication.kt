package com.zerofate.template

import android.app.Application
import android.util.Log

import com.blankj.utilcode.util.LogUtils
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.zerofate.androidsdk.util.Utils

import timber.log.Timber

/**
 * Created by Administrator on 2017/10/23.
 */

class ZTApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
        LogUtils.getConfig().setGlobalTag("ZeroTemplate")
        Stetho.initializeWithDefaults(this)
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        private val TAG = "ZTApplication"
    }
}
