package cn.dozyx.template.transition

import android.content.Intent
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

/**
 * Create by timon on 2019/12/10
 */
open class TransitionTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("default") {
            override fun run() {
                startActivity(Intent(this@TransitionTest, TransitionTest1::class.java))
            }
        })
        addAction(object : Action("theme") {
            override fun run() {
                startActivity(Intent(this@TransitionTest, TransitionTest2::class.java))
            }
        })
        addAction(object : Action("override") {
            override fun run() {
                startActivity(Intent(this@TransitionTest, TransitionTest2::class.java))
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_down)
            }
        })
    }
}

class TransitionTest1 : TransitionTest()
class TransitionTest2 : TransitionTest()
