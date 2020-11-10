package cn.dozyx.template.activity

import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity
import timber.log.Timber

class FinishTest : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("FinishTest.onCreate")
        finish()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("FinishTest.onResume")
    }

    override fun onPause() {
        Timber.d("FinishTest.onPause")
        super.onPause()
    }
}