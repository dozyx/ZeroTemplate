package cn.dozyx.template.view.anim

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import cn.dozyx.core.base.BaseFragment
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.fragment_animator_bell.*
import timber.log.Timber

/**
 * Create by dozyx on 2019/7/9
 */
class BellAnimatorFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("BellAnimatorFragment.onViewCreated: $savedInstanceState")
        image_phone.setOnClickListener {
            val frame0 = Keyframe.ofFloat(0f, 0f)
            val frame1 = Keyframe.ofFloat(0.1f, -20f)
            val frame2 = Keyframe.ofFloat(0.2f, 20f)
            val frame3 = Keyframe.ofFloat(0.3f, -20f)
            val frame4 = Keyframe.ofFloat(0.4f, 20f)
            val frame5 = Keyframe.ofFloat(0.5f, -20f)
            val frame6 = Keyframe.ofFloat(0.6f, 20f)
            val frame7 = Keyframe.ofFloat(0.7f, -20f)
            val frame8 = Keyframe.ofFloat(0.8f, 20f)
            val frame9 = Keyframe.ofFloat(0.9f, -20f)
            val frame10 = Keyframe.ofFloat(1f, 0f)
            val frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10)

            val scaleXFrame0 = Keyframe.ofFloat(0f, 1f)
            val scaleXFrame1 = Keyframe.ofFloat(0.1f, 1.1f)
            val scaleXFrame9 = Keyframe.ofFloat(0.9f, 1.1f)
            val scaleXFrame10 = Keyframe.ofFloat(1f, 1f)
            val scaleXFrameHolder = PropertyValuesHolder.ofKeyframe("scaleX", scaleXFrame0, scaleXFrame1, scaleXFrame9, scaleXFrame10)

            val scaleYFrame0 = Keyframe.ofFloat(0f, 1f)
            val scaleYFrame1 = Keyframe.ofFloat(0.1f, 1.1f)
            val scaleYFrame9 = Keyframe.ofFloat(0.9f, 1.1f)
            val scaleYFrame10 = Keyframe.ofFloat(1f, 1f)
            val scaleYFrameHolder = PropertyValuesHolder.ofKeyframe("scaleX", scaleYFrame0, scaleYFrame1, scaleYFrame9, scaleYFrame10)
            val animator = ObjectAnimator.ofPropertyValuesHolder(image_phone, frameHolder, scaleXFrameHolder, scaleYFrameHolder)
            animator.duration = 1000
            animator.start()
        }
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_animator_bell
    }
}
