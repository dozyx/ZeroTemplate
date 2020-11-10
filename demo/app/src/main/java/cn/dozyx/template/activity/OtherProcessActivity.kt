package cn.dozyx.template.activity

import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class OtherProcessActivity : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("crash") {
            override fun run() {
                throw NullPointerException("crash 呜呜呜")
            }
        })
        addAction(object : Action("thread") {
            override fun run() {
                Thread {
                    Timber.d("OtherProcessActivity.run thread start")
                    Thread.sleep(5 * 1000)
                    Timber.d("OtherProcessActivity.run thread end")
                }.start()
            }
        })
    }
}