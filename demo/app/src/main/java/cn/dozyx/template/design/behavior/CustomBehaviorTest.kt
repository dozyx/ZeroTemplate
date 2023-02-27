package cn.dozyx.template.design.behavior

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.utli.SampleUtil
import cn.dozyx.template.R
import cn.dozyx.template.util.MockUtils
import kotlinx.android.synthetic.main.app_bar_behavior.*

/**
 * Create by dozyx on 2019/6/26
 */
class CustomBehaviorTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (text.layoutParams as CoordinatorLayout.LayoutParams).behavior = CustomBehavior<View>()
        MockUtils.setupList(recycler_view, SampleUtil.getStrings(100))
    }

    //    override fun getLayoutId() = R.layout.activity_custom_behavior
    override fun getLayoutId() = R.layout.app_bar_behavior
}
