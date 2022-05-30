package cn.dozyx.template.view

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_progress_bar.*

class ProgressBarTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_indeterminate.setOnClickListener {
            progress_bar.isIndeterminate = !progress_bar.isIndeterminate
            mpb.isIndeterminate = !mpb.isIndeterminate
//            indicator.isIndeterminate = true // 1.3.0 版本会抛异常
//            mpb.postInvalidate()
        }

    }

    override fun getLayoutId() = R.layout.test_progress_bar
}