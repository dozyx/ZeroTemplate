package cn.dozyx.template.view

import android.os.Bundle
import android.view.View
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_button.*
import timber.log.Timber

class ButtonTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn1.setOnClickListener {
            Timber.d("button1 clicked")
        }
        btn2.visibility = View.INVISIBLE
        btn2.setOnClickListener {
            Timber.d("button2 clicked")
        }
    }
    override fun getLayoutId() = R.layout.test_button
}