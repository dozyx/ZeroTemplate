package cn.dozyx.template

import android.view.Gravity
import android.view.WindowManager
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.ex.dp

class DialogActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_dialog

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val view = window.decorView
        val lp = view.layoutParams as WindowManager.LayoutParams
        lp.gravity = Gravity.BOTTOM
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = 48.dp.toInt()
        windowManager.updateViewLayout(view, lp)
    }
}