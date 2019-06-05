package com.zerofate.template.view

import android.os.Bundle
import com.dozeboy.android.core.base.BaseActivity
import com.zerofate.template.R
import kotlinx.android.synthetic.main.activity_focus_test.*

/**
 * Create by timon on 2019/6/5
 */
class FocusTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edit1.isFocusableInTouchMode = true
        edit1.requestFocus()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_focus_test
    }
}
