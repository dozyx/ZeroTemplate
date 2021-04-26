package cn.dozyx.template.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
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
        addAction(object : Action("启动未注册 service") {
            override fun run() {
                startService(Intent(this@ServiceTest, UnregisterService::class.java))
            }
        })
    }
}

class UnregisterService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
