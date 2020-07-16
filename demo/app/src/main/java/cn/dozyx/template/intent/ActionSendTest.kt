package cn.dozyx.template.intent

import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class ActionSendTest : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("ActionSendTest.onCreate $intent")
    }
    override fun initActions() {

    }
}