package cn.dozyx.template.activity

import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity

class MockThirdAppTest : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appendResult(intent?.action)
    }
}