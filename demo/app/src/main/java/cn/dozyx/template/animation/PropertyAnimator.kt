package cn.dozyx.template.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_property.*
import timber.log.Timber

class PropertyAnimator : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_anim.setOnClickListener {
//            val translationXAnim = ObjectAnimator.ofFloat(btn_anim, "translationX", 240F)
            val translationXAnim = ObjectAnimator.ofFloat(btn_anim, "translationY", -240F)
//            val translationXAnim = ObjectAnimator.ofFloat(btn_anim, "x", 240F)
//            val translationXAnim = ObjectAnimator.ofFloat(btn_anim, "scrollX", 240F)
            translationXAnim.duration = 1000
            translationXAnim.start()
        }
        text_scale.setOnClickListener {
            Timber.d("start anim: width ${text_scale.width} & right ${text_scale.right}")
            // scale 前后的 width 和 right 没有变化。
            // 所以，即使 scale 之后，如果有其他相对于这个 view 的 view，它们的位置并不会产生变化
            val animator = AnimatorSet()
            animator.playTogether(ObjectAnimator.ofFloat(text_scale, "scaleX", 1F, 0.5F),
                    ObjectAnimator.ofFloat(text_scale, "scaleY", 1F, 0.5F))
//            animator.playTogether(ObjectAnimator.ofFloat(text_scale, "textSize", 48F, 12F))
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    Timber.d("PropertyAnimator.onAnimationEnd: width ${text_scale.width} & right ${text_scale.right}")
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })
            animator.start()
        }
    }

    override fun getLayoutId() = R.layout.test_property
}