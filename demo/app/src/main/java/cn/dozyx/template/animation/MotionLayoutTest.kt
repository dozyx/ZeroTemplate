package cn.dozyx.template.animation

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.motion_layout_test.*
import kotlinx.android.synthetic.main.test_motion_layout_expand.*
import timber.log.Timber

class MotionLayoutTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*        button.setOnClickListener{
//            motionLayout.transitionToEnd()
            motionLayout.transitionToState(R.id.transition2)
        }*/
//        btn_visibility_mode.visibility = View.INVISIBLE
        btn_visibility_mode.setOnClickListener {
            Timber.d("MotionLayoutTest click")
            if (fab.isExtended) {
                fab.shrink()
            } else {
                fab.extend()
            }
        }
        motion_layout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                Timber.d("MotionLayoutTest.onTransitionStarted")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                Timber.d("MotionLayoutTest.onTransitionChange $p3")
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                Timber.d("MotionLayoutTest.onTransitionCompleted $p1")
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                Timber.d("MotionLayoutTest.onTransitionTrigger")
            }
        })
    }

    //    override fun getLayoutId() = R.layout.motion_layout_test
    override fun getLayoutId() = R.layout.test_motion_layout_expand
}