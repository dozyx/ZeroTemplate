package cn.dozyx.template.view.anim

import android.animation.Animator
import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_key_board.*
import kotlinx.android.synthetic.main.test_lottie.*
import timber.log.Timber

class LottieTest : BaseActivity() {
    private var isAnimDrawable = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lottie_view.setAnimation(R.raw.live_chat_menu)
        btn_drawable.setOnClickListener {
            if (isAnimDrawable) {
                lottie_view.setImageResource(R.drawable.feedback)
            } else {
                lottie_view.setAnimation(R.raw.live_chat_menu)
            }
            isAnimDrawable = !isAnimDrawable
        }
        btn_start.setOnClickListener {
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

        btn_cancel.setOnClickListener {
            lottie_view.cancelAnimation() // 动画会停留在当前进度
//            lottie_view.progress = 0F
//            lottie_view.clearAnimation() // 不会停止当前的进度
        }
    }
    override fun getLayoutId() = R.layout.test_lottie

}