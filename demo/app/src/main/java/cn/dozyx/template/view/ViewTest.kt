package cn.dozyx.template.view

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_view.*
import timber.log.Timber

class ViewTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button.setOnClickListener {
            Timber.d("ViewTest.onCreate: ${button.top}")
        }
    }

    override fun getLayoutId() = R.layout.test_view
}