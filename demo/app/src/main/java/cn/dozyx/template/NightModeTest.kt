package cn.dozyx.template

import androidx.appcompat.app.AppCompatDelegate
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

class NightModeTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("切换") {
            override fun run() {

            }
        })
    }
}