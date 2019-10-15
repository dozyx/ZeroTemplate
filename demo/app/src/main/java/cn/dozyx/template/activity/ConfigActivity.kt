package cn.dozyx.template.activity

import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity

class ConfigActivity : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("orientation", Runnable {
            appendResult("orientation: ${resources.configuration.orientation}")
        })
        addButton("display orientation", Runnable {
            appendResult("display orientation: ${windowManager.defaultDisplay.rotation}")
        })
    }
}