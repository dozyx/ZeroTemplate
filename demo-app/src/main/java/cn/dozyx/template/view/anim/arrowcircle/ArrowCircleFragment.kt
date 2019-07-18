package cn.dozyx.template.view.anim.arrowcircle

import cn.dozyx.template.view.anim.BaseAnimDemoFragment

/**
 * Create by timon on 2019/7/18
 */
class ArrowCircleFragment : BaseAnimDemoFragment<ArrowCircleView>() {
    override fun getAnimView(): ArrowCircleView {
        return ArrowCircleView(context)
    }
}
