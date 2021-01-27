package cn.dozyx.template.message

import android.content.Intent
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity

class BroadcastTest : BaseTestActivity() {
    override fun initActions() {
        addAction(object : Action("发送") {
            override fun run() {
                sendBroadcast(Intent("snap.cleaner.action.JUNK_STATUS_CHANGED"))
            }
        })
    }
}