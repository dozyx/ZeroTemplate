package cn.dozyx.template.view.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import cn.dozyx.core.base.BaseFragment
import cn.dozyx.template.R
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_expeand_menu.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * Create by dozyx on 2019/7/9
 */
class ExpandMenuFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (!isMenuOpen) {
            isMenuOpen = true
            openMenu()
        } else {
            ToastUtils.showShort("点击了$v")
            isMenuOpen = false
            closeMenu()
        }
    }

    private var isMenuOpen = false
    override fun getLayoutId(): Int {
        return R.layout.fragment_expeand_menu
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_menu.setOnClickListener(this)
        btn_item1.setOnClickListener(this)
        btn_item2.setOnClickListener(this)
        btn_item3.setOnClickListener(this)
        btn_item4.setOnClickListener(this)
        btn_item5.setOnClickListener(this)
    }

    private fun closeMenu() {
        doAnimationClose(btn_item1, 0, 5, 300)
        doAnimationClose(btn_item2, 1, 5, 300)
        doAnimationClose(btn_item3, 2, 5, 300)
        doAnimationClose(btn_item4, 3, 5, 300)
        doAnimationClose(btn_item5, 4, 5, 300)
    }

    private fun openMenu() {
        doAnimationOpen(btn_item1, 0, 5, 300)
        doAnimationOpen(btn_item2, 1, 5, 300)
        doAnimationOpen(btn_item3, 2, 5, 300)
        doAnimationOpen(btn_item4, 3, 5, 300)
        doAnimationOpen(btn_item5, 4, 5, 300)
    }

    private fun doAnimationOpen(view: View, index: Int, total: Int, radius: Int) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        val degree = Math.toRadians(90.0) / (total - 1) * index
        val translationX = -radius * sin(degree).toFloat()
        val translationY = -radius * cos(degree).toFloat()
        val set = AnimatorSet()
        set.playTogether(ObjectAnimator.ofFloat(view, "translationX", 0f, translationX), ObjectAnimator.ofFloat(view, "translationY", 0f, translationY), ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f), ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f), ObjectAnimator.ofFloat(view, "alpha", 0f, 1f))
        set.setDuration(500).start()
    }

    private fun doAnimationClose(view: View, index: Int, total: Int, radius: Int) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        val degree = Math.PI * index / ((total - 1) * 2)
        val translationX = -radius * sin(degree).toFloat()
        val translationY = -radius * cos(degree).toFloat()
        val set = AnimatorSet()
        set.playTogether(ObjectAnimator.ofFloat(view, "translationX", translationX, 0f), ObjectAnimator.ofFloat(view, "translationY", translationY, 0f), ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f), ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f), ObjectAnimator.ofFloat(view, "alpha", 1f, 0f))
        set.setDuration(500).start()
    }

}
