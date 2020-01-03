package cn.dozyx.template.service

import com.blankj.utilcode.util.ServiceUtils

import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

class ServiceTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("启动") {
            override fun run() {
                ServiceUtils.startService(MyIntentService::class.java)
            }
        })
    }
}
