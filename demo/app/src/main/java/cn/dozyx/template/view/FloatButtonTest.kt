package cn.dozyx.template.view

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.test_float_button.*

class FloatButtonTest : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fab.setOnClickListener {
            Snackbar.make(it, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    override fun getLayoutId() = R.layout.test_float_button
}