package com.dozeboy.android.core.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * @author dozeboy
 * @date 2018/11/21
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutView() != null) {
            setContentView(getLayoutView())
        } else {
            setContentView(getLayoutId())
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun getLayoutView(): View? = null
}