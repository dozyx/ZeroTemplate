package cn.dozyx.template.pop

import android.content.Intent
import cn.dozyx.template.activity.SimpleAdActivity
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import cn.dozyx.template.view.CustomViewActivity
import timber.log.Timber

/**
 * @author dozyx
 * @date 11/17/20
 */
class PopElementTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("二级页面") {
            override fun run() {
                startActivity(Intent(this@PopElementTest, CustomViewActivity::class.java))
            }
        })

        addAction(object : Action("check") {
            override fun run() {
                Timber.d("PopElementTest check canPop: ${HomePopTracker.canPop()}")
            }
        })

        addAction(object : Action("广告") {
            override fun run() {
                startActivity(Intent(this@PopElementTest, SimpleAdActivity::class.java))
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Timber.d("PopElementTest.onResume canPop: ${HomePopTracker.canPop()}")
    }
}