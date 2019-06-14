package com.dozeboy.core.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Create by timon on 2019/6/14
 **/
abstract class BaseSingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(this)
        val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        frameLayout.layoutParams = params
        frameLayout.id = View.generateViewId()
        setContentView(frameLayout)
        supportFragmentManager.beginTransaction().add(frameLayout.id,
                getFragment(intent)).commit()
    }

    /**
     * 提供Fragment
     *
     * @param startIntent 从启动 activity 的 intent 中获取参数
     * @return fragment
     */
    abstract fun getFragment(startIntent: Intent): Fragment
}