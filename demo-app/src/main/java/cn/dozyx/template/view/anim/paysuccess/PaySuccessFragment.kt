package cn.dozyx.template.view.anim.paysuccess

import cn.dozyx.template.view.anim.BaseAnimDemoFragment

/**
 * Create by timon on 2019/7/18
 */
class PaySuccessFragment : BaseAnimDemoFragment<PaySuccessView>() {
    override fun getAnimView(): PaySuccessView {
        return PaySuccessView(context)
    }
}
