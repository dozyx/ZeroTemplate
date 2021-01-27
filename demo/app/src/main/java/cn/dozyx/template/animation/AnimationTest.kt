package cn.dozyx.template.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.animation_test.*
import timber.log.Timber

class AnimationTest : BaseActivity() {
    private val translateAnim: TranslateAnimation = TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0F, Animation.RELATIVE_TO_PARENT, 1F, Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, 0F)
    private lateinit var objectAnimator: ObjectAnimator

    init {
        translateAnim.apply {
            repeatCount = 3
            repeatMode = Animation.RESTART
            duration = 2000
            interpolator = DecelerateInterpolator()
            fillAfter = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        objectAnimator = ObjectAnimator.ofFloat(image_anim, "translationX", 0F, 300F)
        objectAnimator.duration = 2000
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                Timber.d("AnimationTest.onAnimationRepeat")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Timber.d("AnimationTest.onAnimationEnd")
            }

            override fun onAnimationCancel(animation: Animator?) {
                Timber.d("AnimationTest.onAnimationCancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Timber.d("AnimationTest.onAnimationStart")
            }
        })
        start.setOnClickListener {
            startTranslationAnim()
        }
        cancel.setOnClickListener {
            stopTranslationAnim()
        }
        start_property.setOnClickListener {
            objectAnimator.start()
        }
        cancel_property.setOnClickListener {
            objectAnimator.cancel()
        }
        pause_property.setOnClickListener {
            objectAnimator.pause()
        }
        resume_property.setOnClickListener {
            objectAnimator.resume()
        }
        bt_log.setOnClickListener {
            Timber.d("getLeft ${image_anim.left}")
        }
    }

    private fun stopTranslationAnim() {
        translateAnim.cancel()// view 会恢复到原来位置
    }

    private fun startTranslationAnim() {
        image_anim.animation = translateAnim
        translateAnim.start()// 动画要等到 invalidate 时才生效
//        image_anim.startAnimation(translateAnim)
    }

    override fun getLayoutId(): Int {
        return R.layout.animation_test
    }
}
