package cn.dozyx.template.view.anim

import android.animation.Animator
import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_lottie.*
import timber.log.Timber

class LottieTest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_start.setOnClickListener {
            lottie_view.setAnimation(R.raw.lottie_logo)
            lottie_view.playAnimation()
            Timber.d("LottieTest.onCreate duration: ${lottie_view.duration}")
            lottie_view.addAnimatorListener(object :Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {
                    Timber.d("LottieTest.onAnimationStart")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Timber.d("LottieTest.onAnimationEnd")
                }

                override fun onAnimationCancel(animation: Animator?) {
                    Timber.d("LottieTest.onAnimationCancel")
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    Timber.d("LottieTest.onAnimationRepeat")
                }
            })
        }
    }
    override fun getLayoutId() = R.layout.test_lottie

}