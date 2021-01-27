package cn.dozyx.template.transition

import android.content.Intent
import cn.dozyx.template.R
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

/**
 * Create by dozyx on 2019/12/10
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
                val intent = Intent(this@TransitionTest, TransitionTest2::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)// 如果启动的 Activity 与当前 Activity 的任务栈不同，windowAnimationStyle 会无效。
//                但是 overridePendingTransition 有效。也就是任务栈的第一个 Activity 只能使用默认动画。
                startActivity(intent)
            }
        })
        addAction(object : Action("override") {
            override fun run() {
                startActivity(Intent(this@TransitionTest, TransitionTest2::class.java))
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_down)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_left)
            }
        })
    }
}

class TransitionTest1 : TransitionTest()
class TransitionTest2 : TransitionTest()
