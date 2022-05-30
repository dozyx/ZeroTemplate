package cn.dozyx.template.device

import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.NetworkUtils

class DeviceInfoTest : BaseTestActivity() {

    override fun initActions() {
        addAction(object : Action("IP") {
            override fun run() {
                showIP()
            }
        })
    }

    private fun showIP() {
        appendResult(NetworkUtils.getIPAddress(true))
        appendResult(NetworkUtils.getIPAddress(false))
        appendResult(cn.dozyx.template.device.NetworkUtils.getLocalIpAddress(this))
        appendResult("#########")
    }

}