package cn.dozyx.template

import android.app.Application
import cn.dozyx.core.base.BaseApplication

import com.blankj.utilcode.util.LogUtils
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

import timber.log.Timber

/**
 * Created by Administrator on 2017/10/23.
 */

class ZTApplication : BaseApplication() {

    override fun initOnMainProcess() {
        Stetho.initializeWithDefaults(this)
    }

    override fun initOnAllProcess() {

    }

    companion object {
        private val TAG = "ZTApplication"
    }
}
