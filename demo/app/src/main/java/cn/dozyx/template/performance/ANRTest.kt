package cn.dozyx.template.performance

import android.os.Bundle
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class ANRTest : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Thread.sleep(30 * 1000)
    }

    override fun initActions() {
        addAction(object : Action("sleep") {
            override fun run() {
                Timber.d("ANRTest sleep start")
                Thread.sleep(30 * 1000)
                Timber.d("ANRTest sleep end")
            }
        })

        addAction(object : Action("click") {
            override fun run() {
                Timber.d("ANRTest click")
            }
        })
    }
}