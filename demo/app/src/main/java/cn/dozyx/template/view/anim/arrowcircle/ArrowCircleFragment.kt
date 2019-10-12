package cn.dozyx.template.view.anim.arrowcircle

import cn.dozyx.template.view.anim.BaseSingleViewDemoFragment

/**
 * Create by dozyx on 2019/7/18
 */
class ArrowCircleFragment : BaseSingleViewDemoFragment<ArrowCircleView>() {
    override fun getAnimView(): ArrowCircleView {
        return ArrowCircleView(context)
    }
}
