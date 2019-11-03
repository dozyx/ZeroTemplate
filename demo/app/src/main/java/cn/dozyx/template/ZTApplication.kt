package cn.dozyx.template

import cn.dozyx.core.base.BaseApplication
import com.facebook.stetho.Stetho


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
