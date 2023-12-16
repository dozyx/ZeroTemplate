package cn.dozyx.template.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.lifecycleScope
import cn.dozyx.template.base.Action

import cn.dozyx.template.base.BaseShowResultActivity
import cn.dozyx.template.base.BaseTestActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ServiceLifeCycleActivity : BaseTestActivity() {
    override fun initActions() {

    }

    private var serviceIntent: Intent? = null
    private lateinit var connection: ServiceConnection
    private lateinit var connection2: ServiceConnection
    // 一开始怀疑多次绑定只调用 onBind 一次是否与绑定使用的是同一个ServiceConnection有关，事实证明多个客户端绑定的确只调用一次

    private var simpleService: SimpleService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent(this, SimpleService::class.java)
        connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                appendResult("onServiceConnected ")
                simpleService = (service as ServiceBinder).service
            }

            override fun onServiceDisconnected(name: ComponentName) {
                appendResult("onServiceDisconnected ")
            }
        }

        connection2 = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                appendResult("onServiceConnected ")
            }

            override fun onServiceDisconnected(name: ComponentName) {
                appendResult("onServiceDisconnected ")
            }
        }

        addAction(object : Action("start") {
            override fun run() {
                startService(serviceIntent)
            }
        })

        addAction(object : Action("start delay") {
            override fun run() {
                lifecycleScope.launch {
                    delay(5000)
                    startService(serviceIntent)
                }
            }
        })

        addAction(object : Action("start foreground") {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent)
                }
            }
        })
        addAction(object : Action("bind") {
            override fun run() {
                bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
            }
        })
        addAction(object : Action("unbind") {
            override fun run() {
                unbindService(connection)
            }
        })
        addAction(object : Action("stop") {
            override fun run() {
                stopService(serviceIntent)
            }
        })
        addAction(object : Action("bind app") {
            override fun run() {
                applicationContext.bindService(serviceIntent, connection!!, Context.BIND_AUTO_CREATE)
            }
        })
        addAction(object : Action("unbind app") {
            override fun run() {
                applicationContext.unbindService(connection!!)
            }
        })
        addAction(object : Action("another activity") {
            override fun run() {
                startActivity(Intent(this@ServiceLifeCycleActivity, ServiceLifeCycleActivity::class.java))
            }
        })

        appendResult("\ngetApplication == " + application + "\ngetApplicationContext == "
                + applicationContext)
        appendResult("\n多个客户端绑定服务，onBind只在第一次绑定时回调")
        appendResult(
                "\n假如服务正处于运行状态，并且已执行过绑定和解绑，这时候进行绑定，同样不会会调用 " + "onBind。即服务在第一次绑定时会将IBinder对象保存，后续直接传递给ServiceConnection对象，而不需要通过onBind")
        appendResult("\n在Context被销毁时，如果没有解绑将自动unbind")
        appendResult("\n服务有绑定的客户端，那么调用 stopService 是不能销毁该服务的，Service 的 StopSelf 同样不行。")
    }
}
