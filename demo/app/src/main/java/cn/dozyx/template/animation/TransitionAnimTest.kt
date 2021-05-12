package cn.dozyx.template.animation

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionValues
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_transition_anim.*


/**
 * @author dozyx
 * @date 5/12/21
 */
class TransitionAnimTest : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iv_anim.setOnClickListener {
            TransitionManager.beginDelayedTransition(root)
            (iv_anim.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.END
                width *= 2
                height *= 2
            }
            iv_anim.requestLayout()
        }
    }

    override fun getLayoutId() = R.layout.test_transition_anim
}

class CustomTransition : Transition() {
    override fun captureStartValues(transitionValues: TransitionValues) {

    }

    override fun captureEndValues(transitionValues: TransitionValues) {
    }
}