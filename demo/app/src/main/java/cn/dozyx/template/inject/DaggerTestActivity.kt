package cn.dozyx.template.inject

import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity

class DaggerTestActivity : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("启动", Runnable {

        })
    }
}



