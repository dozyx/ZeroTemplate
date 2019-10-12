package cn.dozyx.template.view.anim

import android.os.Bundle
import android.view.animation.AnimationUtils

import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_scan_circel.*

/**
 * Create by dozyx on 2019/7/8
 */
class ScanCircleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation1 = AnimationUtils.loadAnimation(this, R.anim.scan_alpha)
        val animation2 = AnimationUtils.loadAnimation(this, R.anim.scan_alpha)
        val animation3 = AnimationUtils.loadAnimation(this, R.anim.scan_alpha)
        val animation4 = AnimationUtils.loadAnimation(this, R.anim.scan_alpha)
        circle1.startAnimation(animation1)
        animation2.startOffset = 600
        circle2.startAnimation(animation2)
        animation3.startOffset = 1200
        circle3.startAnimation(animation3)
        animation4.startOffset = 1800
        circle4.startAnimation(animation4)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_scan_circel
    }
}
