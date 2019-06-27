package cn.dozyx.template.design.behavior

import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import cn.dozyx.template.design.CustomBehavior
import com.google.android.material.behavior.SwipeDismissBehavior
import kotlinx.android.synthetic.main.activity_custom_behavior.*

/**
 * Create by timon on 2019/6/26
 */
class CustomBehaviorTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        (text.layoutParams as CoordinatorLayout.LayoutParams).behavior = CustomBehavior<View>()
    }

    override fun getLayoutId() = R.layout.activity_custom_behavior
}
