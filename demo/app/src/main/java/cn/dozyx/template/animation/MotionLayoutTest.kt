package cn.dozyx.template.animation

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.motion_layout_test.*

class MotionLayoutTest:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button.setOnClickListener{
//            motionLayout.transitionToEnd()
            motionLayout.transitionToState(R.id.transition2)
        }
    }
    override fun getLayoutId() = R.layout.motion_layout_test
}