package cn.dozyx.template.activity

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_simple.*

/**
 * @author dozyx
 * @date 11/18/20
 */
abstract class SimpleActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_label.text = javaClass.simpleName
    }

    override fun getLayoutId() = R.layout.activity_simple
}

class SimpleAdActivity : SimpleActivity()