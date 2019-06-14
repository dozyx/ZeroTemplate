package cn.dozyx.template.socket

import android.os.Bundle
import cn.dozyx.template.base.BaseTestActivity

class UdpActivity : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("启动客户端", Runnable {

        })
        addButton("启动服务端", Runnable {

        })
    }
}

class ClientThread : Thread() {
    override fun run() {
        super.run()
    }
}

class ServerThread : Thread() {
    override fun run() {
        super.run()
    }
}
