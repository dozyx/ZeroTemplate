package cn.dozyx.template.view.anim

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import cn.dozyx.core.base.BaseFragment
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.base_fragment_anim_demo.*

/**
 * Create by dozyx on 2019/7/18
 */
abstract class BaseSingleViewDemoFragment<V : View> : BaseFragment() {
    protected lateinit var animView1: V

    abstract fun getAnimView(): V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animView1 = getAnimView()
        if (animView1.layoutParams == null) {
            animView1.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        ll_content.addView(animView1)
    }

    override fun getLayoutId(): Int {
        return R.layout.base_fragment_anim_demo
    }
}
