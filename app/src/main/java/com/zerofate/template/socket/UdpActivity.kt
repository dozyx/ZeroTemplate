package com.zerofate.template.socket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zerofate.template.base.BaseGridButtonActivity

class UdpActivity : BaseGridButtonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("启动客户端", Runnable {

        })
        addButton("启动服务端", Runnable {

        })
    }

}
