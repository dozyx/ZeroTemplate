package cn.dozyx.template.system

import android.content.Intent
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

class OrientationTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("竖屏") {
            override fun run() {
                val intent = Intent(this@OrientationTest, LandscapeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
        })
    }
}