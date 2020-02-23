package cn.dozyx.template.performance

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.utli.util.ColorUtil
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.test_ui_performance.*

/**
 * @author dozyx
 * @date 2020-02-14
 */
class UIPerformanceTest : BaseActivity() {
    private var margin = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setBackgroundDrawable(ColorDrawable(ColorUtil.random()))
        btn_add_view.setOnClickListener {
            val view = View(this)
            val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            margin += 50
            params.leftMargin = margin
            view.layoutParams = params
            view.setBackgroundColor(ColorUtil.random())
            fl_views.addView(view)
        }
    }
    override fun getLayoutId(): Int {
        return R.layout.test_ui_performance
    }
}