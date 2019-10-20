package cn.dozyx.template.demo

import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import cn.dozyx.template.demo.swipedismiss.DragDismissActivity
import com.blankj.utilcode.util.ActivityUtils

/**
 * 集合自己模仿其他 app 的功能
 * @author dozyx
 * @date 2019-10-15
 */
class DemoActivity : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("拖动关闭") {
            override fun run() {
                ActivityUtils.startActivity(DragDismissActivity::class.java)
            }
        })
    }
}
